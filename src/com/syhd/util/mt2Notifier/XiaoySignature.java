package com.syhd.util.mt2Notifier;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * 接口签名验证
 * @author puci
 *
 */
public class XiaoySignature {
	
	//private static final Logger log  = LogUtil.getSignLog();
    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o,String privateKey) throws IllegalAccessException {
        Map<String, String> paramMap = paraFilter(ReflectBeanUtil.bean2MapString(o));
        String needSign = createLinkString(paramMap);
        String sign = null;
        try {
        	System.out.println("needSign:" + needSign);
			sign = RSACoder.sign(needSign.getBytes(), privateKey);
			System.out.println("Sign Result:" + sign);
		} catch (Exception e) {
			System.out.println("签名异常：" + e.getMessage());
		}
        return sign;
    }
    
    public static String getSign(Map<String,String> map,String privateKey){
    	Map<String, String> paramMap = paraFilter(map);
        String linkedString = createLinkString(paramMap);
        String needSign = linkedString.toString();
        String sign = null;
        try {
			sign = RSACoder.sign(needSign.getBytes(), privateKey);
			System.out.println("needSign:" + needSign);
			System.out.println("Sign Result:" + sign);
		} catch (Exception e) {
			System.out.println("签名异常：" + e.getMessage());
		}
        return sign;
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @param responseString API返回的XML数据字符串
     * @param publicKey 商户公钥
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString,String publicKey) throws ParserConfigurationException, IOException, SAXException {

        Map<String,Object> map = JsonUtil.parseJSON2Map(responseString);
        System.out.println("responseString map : " + map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        boolean result = false;
		try {
			//验证签名，用商户的公钥去验证
			Map<String, String> paramMap = paraFilter(ReflectBeanUtil.MapObj2MapString(map));
	        String cryptData = createLinkString(paramMap);
			result = RSACoder.verify(cryptData.getBytes(), publicKey, URLDecoder.decode(signFromAPIResponse, "UTF-8"));
		} catch (IllegalAccessException e) {
			System.out.println("验证签名异常：" + e.getMessage());
		} catch (Exception e) {
			System.out.println("验证签名异常：" + e.getMessage());
		}

        if(!result){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
        	System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("payChannel")
            		|| key.equalsIgnoreCase("transID")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
}

package com.syhd.util.mt2Notifier;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 * 
 */
public abstract class Coder {
	public static final String ENCODING = "utf-8";
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_MD5_SHA = "HmacSHA1";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC_MD5 = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC_MD5);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC_MD5);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}
	/**
	 * HmacSHA1加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptHMACMD5SHA(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(ENCODING), KEY_MD5_SHA);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		
		return bytesToHexString(mac.doFinal(data));
		
	}
	
	 public static String getSortString(Map<String, String> paras){
	        ArrayList<String> list = new ArrayList<String>();
	        for(Map.Entry<String,String> entry:paras.entrySet()){
	            if(entry.getValue()!="" && StringUtils.isNotBlank(String.valueOf(entry.getValue()))){
	                list.add(entry.getKey() + "=" + entry.getValue() + "&");
	            }
	        }
	        int size = list.size();
	        String [] arrayToSort = list.toArray(new String[size]);
	        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < size; i ++) {
	            sb.append(arrayToSort[i]);
	        }
	        String result = sb.toString();
	        if(result.endsWith("&")){
	        	result = result.substring(0,result.length()-1);
	        }
	        return result;
	 }
	 public static String getSortValueString(Map<String, String> paras){
		 ArrayList<String> list = new ArrayList<String>();
		 for(Map.Entry<String,String> entry:paras.entrySet()){
			 if(entry.getValue()!="" && StringUtils.isNotBlank(String.valueOf(entry.getValue()))){
				 list.add(entry.getKey() + "=" + entry.getValue());
			 }
		 }
		 int size = list.size();
		 String [] arrayToSort = list.toArray(new String[size]);
		 Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		 StringBuilder sb = new StringBuilder();
		 for(int i = 0; i < size; i ++) {
			 sb.append(arrayToSort[i].split("=")[1]);
		 }
		 String result = sb.toString();
		 return result;
	 }
	 
	public static String bytesToHexString(byte[] bytesArray) {
		if (bytesArray == null) {
			return null;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (byte b : bytesArray) {
			String hv = String.format("%02x", b);
			sBuilder.append(hv);
		}
		return sBuilder.toString();
	}
}

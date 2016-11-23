package com.syhd.httpSender;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.syhd.util.XiaoyHttpUtil;
import com.syhd.util.mt2Notifier.JsonUtil;
import com.syhd.util.mt2Notifier.PayNotifyRequestData;
import com.syhd.util.mt2Notifier.ReflectBeanUtil;
import com.syhd.util.mt2Notifier.XiaoySignature;

public class blazBlueNotifier {
	public static void main(String[] args) throws Exception {
		blazBlueNotifier();
	}


	public static void blazBlueNotifier() throws UnsupportedEncodingException {
		String url = "http://myci.91act.com:10033/";
		String corpId = "CM00001002";
		String appId = "APP00001002";
		String xiaoyTradeNo = "xy1720160509146279512150145184";
		String outTradeNo = "462823813206-79737-83967-16387";
		String payChannel = "17";
		String orderCreateTime = "20160509195830";
		String orderExpireTime = "20160509195841";
		String attach = "10011605091956020064";
		String privatekey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOf7LbZ7Mko3ckTB6/ptXRQDq45i4WeqvodGD94VlfCpWZUDk3DgcSoGBDrxe4NplbvWac04b9Pchyv/odV0feV0QglVz/6JbjIecrAlclcuF+ZOeww4g0G4qRpKcsSoOWoVR7SAU+MOv9aLhqzTPuGK+CGQWYakIFo1fswJ4rXXAgMBAAECgYEAhHzClcZ0aUiTXUt3bzXIg+a4swAsBZ3RZMaRx1+Cm8jMXbuGGGBGoR6Aif1iciH6HyYMlOUXkOMQ3AMKNTNrtmq2Ht0Jv3uauTsP2bVD+5x921XZaTpLF42qiwFDdwN04enTJlid/4AMj+Rsc4usRgbDCxKcE1qPY17jOy7XKhkCQQD2+yTiUM3BfRr8lxt3wXEn+KrVn5Q5z3jVqjIoU9CSKxmyucOKDGXNNqA+DeW5/uhuA1SxG61lPJPe3s+pRG5DAkEA8HPPoVovRh4aXm3HdjsNgzqjzHytjthSr6SqyIMK4hUW66d1bS1CMFH7jOgpPUvAwuKGpS3YGx7NFlJUD5EC3QJASwCNfOTI9x9E2LwSrVVjRZ4wUts5Ki0lJs2embyKNDk+fpYHGZ8WMzGJjA6wWsFcWDxOtdIP4BR7W00Shvau/QJBALQxheLcK9s3CfnD+RtQK9MxKbk/oe0Pjf+UvmufUJOWzGNzThuwNA70EThKb0VBNMaXbeHxVicU0QquTdKQkH0CQG/VwLy00QjqwLv6oqZ+i6XpsSoCTlwe25Yp/pjsUrpq5+DnZ9mkw2s2WUi2sdwOpUogctQ5XlBbdjOLpoLhVjM=";
		int totalFee = 100;
		int cashFee = 100;
		PayNotifyRequestData payNotifyRequestData = new PayNotifyRequestData(corpId, appId, xiaoyTradeNo, outTradeNo, payChannel, orderCreateTime, orderExpireTime, totalFee,
				cashFee, attach);
		Map<String, String> mapParam = ReflectBeanUtil.bean2MapString(payNotifyRequestData);
		String sign = XiaoySignature.getSign(mapParam, privatekey);
				payNotifyRequestData.setSign(URLEncoder.encode(sign.replace("\n", ""), "UTF-8"));
		String sendJson = JsonUtil.getJsonString4JavaPOJO(payNotifyRequestData);
		System.out.println("notify:" + url + "  sendJson:" + sendJson);
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		System.out.println("result:" + result);
	}
}

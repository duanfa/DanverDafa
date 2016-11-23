package com.syhd.httpSender;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.syhd.util.XiaoyHttpUtil;
import com.syhd.util.mt2Notifier.JsonUtil;
import com.syhd.util.mt2Notifier.PayNotifyRequestData;
import com.syhd.util.mt2Notifier.ReflectBeanUtil;
import com.syhd.util.mt2Notifier.XiaoySignature;

public class StreamSender {
	public static void main(String[] args) throws Exception {
		postNotify();
		// testNotify();
		// testQuery();
	}

	public static void mt2WangsuNotify() throws Exception {
		String url = "http://pay.stvgame.com/syhd-pay-gateway/mt2notifyaction_wangsunotifier.action";
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", "4853101");
		params.put("price", "1.00");
		params.put("prodName", "符石");
		params.put("extensionInfo", "{\"appKey\":\"9898884358\",\"develName\":\"xx\",\"appName\":\"我叫MT2\",\"packageName\":\"com.stvgame.mt2\"}");
		params.put("status", "PAID");
		params.put("orderCode", "TVAPtv0120160505210943758088");
		params.put("prodNum", "1");
		params.put("payTime", "1462453794000");
		params.put("sellerOrderCode", "462538872863-24687-92843-63036");
		params.put("note", "");
		String result = XiaoyHttpUtil.sendKVPost(url, params);
		System.out.println("result:" + result);
	}

	public static void mt2WangsuNotify_fake() throws Exception {
		String url = "http://pay.stvgame.com/syhd-pay-gateway/mt2notifyaction_wangsunotifier.action";
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", "4853101");
		params.put("price", "98.00");
		params.put("prodName", "符石");
		params.put("extensionInfo", "{\"appKey\":\"9898884358\",\"develName\":\"xx\",\"appName\":\"我叫MT2\",\"packageName\":\"com.stvgame.mt2\"}");
		params.put("status", "PAID");
		params.put("orderCode", "TVAPtv0120160505210943758088");
		params.put("prodNum", "1");
		params.put("payTime", "1462453794000");
		params.put("sellerOrderCode", "462528010689-70164-47439-60389");
		params.put("note", "");
		String result = XiaoyHttpUtil.sendKVPost(url, params);
		System.out.println("result:" + result);
	}

	public static void postNotify() throws Exception {

		String url  = "http://pay.stvgame.com/syhd-pay-gateway/mt2notifyaction_qianbaonotifier.action";
		Map<String, String> params = new HashMap<String, String>();
		params.put("money","30");
		params.put("responseCode","1000");
		params.put("errorCode","");
		params.put("errorMsg","");
		params.put("data","{\"bizCode\":\"46715731775897496766\",\"sdkflowId\":806313}");
		params.put("signCode","f0/TrylcxAVZgwb30tH7ICkEmDDPaPnMu7R/bXQunxuzE6n05lk/GUPvs2QuD05aO2v1SsAQQEzpyfuHNt+m6uDFigLohExfsEf4eoIrmyP6Vug2cY2+wNeJyBJdLNwWP1HKjcV4aX+I1jDVson0FVDhkI6C/j6Uxnv4XHJb6TI=");
		String result = XiaoyHttpUtil.sendKVPost(url, params);
		System.out.println("result:" + result);
	}

	public static void daMaiNotify() {
		String url = "http://pay.stvgame.com/syhd-pay-gateway/titannotifyaction_damainotifier.action";
		String sendJson = "cashAmt=6.0&chargingDegree=-1&chargingDuration=-1&chargingId=-1&chargingName=钻石&chargingPrice=6.0&devicecode=aK51A823J&mac=14:3d:f2:26:c9:41&mlAmt=6.0&orderId=3002016050623555808460728&orderStatus=2&orderTime=2016-05-06 23:55:58&orderAppend={\"orderId\":\"CB578C6F2B2C4B05A96D35637BBBB2F0\"}&payFreeml=0.0&payRealml=6.0&payTime=2016-05-06 23:59:46&payType=4&productId=-1&productName=钻石&productType=2008&sn=DMA30304141212074&userId=1471981&userName=ak51a823j10&sign=cbddaf7efe541b57312d1559cd042e86";
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		System.out.println("result:" + result);
	}

	public static void testPrePay() {
		String url = "http://127.0.0.1:9080/syhd-pay-gateway/gatewayAction_prePay.action";
		url = "http://pay.stvgame.com/syhd-pay-gateway/gatewayAction_prePay.action";
		String sendJson = "{\"detail\":\"10符石\",\"outTradeNo\":\"12345666666\",\"totalFee\":\"100\",\"transID\":\"fdfca78b-2c21-4823-b323-bf9a93c4444\",\"payChannel\":\"08\",\"corpId\":\"APP00001013\",\"body\":\"%E7%AC%A6%E7%9F%B3\",\"appId\":\"APP00001007\",\"attach\":\"attach\",\"notifyUrl\":\"http:\\/\\/billing.locojoy.com\\/api\\/nosdk\\/notify\\/stvpay.ashx\"}";
		sendJson = "{\"detail\":\"10符石\",\"outTradeNo\":\"325db3sassbre642356\",\"totalFee\":\"200\",\"transID\":\"fdbfaca122c2swredf1-2354823-b323-bf9a93c0d995\",\"payChannel\":\"08\",\"corpId\":\"CM00001002\",\"body\":\"%E7%AC%A6%E7%9F%B3\",\"appId\":\"APP00001002\",\"attach\":\"attach\",\"notifyUrl\":\"http:\\/\\/billing.locojoy.com\\/api\\/nosdk\\/notify\\/stvpay.ashx\"}";
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		System.out.println("result:" + result);
	}

	public static void testNotify() {
		String url = "http://pay.stvgame.com/syhd-pay-gateway/mt2notifyaction_qianbaonotifier.action?money=30&responseCode=1000&errorCode=&errorMsg=&data={\"bizCode\":\"46715721567231616482\",\"sdkflowId\":806233}&signCode=UHc4xuGRamsKU4P/DuItShk9zcq1PzfFmkizRJiAedX41LoTvUW9ErwT7AVTa9nz+S2gmCTaQXAhRNbkDbk8r86rAD/KilfFX56TlDy2KVBxTZJK8JEm5SfWh86oFvFZ5zvGVvMOVysTA3P11+3ov138H5yDusVz4VhNc9HJr3Q=";
		String result = XiaoyHttpUtil.sendGet(url);
		System.out.println("result:" + result);
	}

	public static void testQuery() {
		String url = "http://127.0.0.1:9080/syhd-pay-gateway/gatewayAction_query.action";
		String sendJson = "{\"outType\":\"1\",\"outTradeNo\":\"1234567901\",\"appId\":\"APP00001007\",\"xiaoyTradeNo\":\"xy0820160401145950036222867789\",\"sign\":\"08\"}";
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		System.out.println("result:" + result);
	}

	public static void mt2Notifier() throws UnsupportedEncodingException {
		String url = "http://billing.locojoy.com/api/nosdk/notify/stvpay.ashx";
		String corpId = "CM00001002";
		String appId = "APP00001003";
//		appId = "20150807173518455682";
		String xiaoyTradeNo = "xy1720160509146279512150145184";
		String outTradeNo = "462823813206-79737-83967-16387";
		outTradeNo = "462903478615-36388-84413-36922";
		outTradeNo = "46505488283745009777";   //随便找的订单
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

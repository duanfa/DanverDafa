package com.syhd.httpSender;

import net.sf.json.JSONObject;

import com.syhd.util.XiaoyHttpUtil;

public class XiaoyWatcher {
	public static void main(String[] args) throws Exception {
		watcherPrePay();
	}

	public static void watcherPrePay() {
		boolean ali =false,wechat =false;
		String url = "http://pay.stvgame.com/syhd-pay-gateway/gatewayAction_prePay.action";
		Long current = System.currentTimeMillis();
		String outTradeNo = "outTradeNo_"+current;
		String transID = "transID_"+current;
		String sendJson = "{\"detail\":\"10符石\",\"outTradeNo\":\""+outTradeNo+"\",\"totalFee\":\"100\",\"transID\":\""+transID+"\",\"payChannel\":\"03\",\"corpId\":\"CM00001002\",\"body\":\"%E7%AC%A6%E7%9F%B3\",\"appId\":\"APP00001020\",\"attach\":\"attach\",\"notifyUrl\":\"http:\\/\\/billing.locojoy.com\\/api\\/nosdk\\/notify\\/stvpay.ashx\"}";
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		JSONObject aliJson = JSONObject.fromObject(result);
		if(aliJson.getString("codeUrl").startsWith("https://qr.alipay.com")){
			ali = true;
		}
		url = "http://pay.stvgame.com/syhd-pay-gateway/gatewayAction_prePay.action";
		 current = System.currentTimeMillis();
		 outTradeNo = "outTradeNo_"+current;
		 transID = "transID_"+current;
		 sendJson = "{\"detail\":\"10符石\",\"outTradeNo\":\""+outTradeNo+"\",\"totalFee\":\"100\",\"transID\":\""+transID+"\",\"payChannel\":\"02\",\"corpId\":\"CM00001002\",\"body\":\"%E7%AC%A6%E7%9F%B3\",\"appId\":\"APP00001020\",\"attach\":\"attach\",\"notifyUrl\":\"http:\\/\\/billing.locojoy.com\\/api\\/nosdk\\/notify\\/stvpay.ashx\"}";
		 result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		 JSONObject wechatJson = JSONObject.fromObject(result);
		 if(wechatJson.getString("codeUrl").startsWith("weixin://wxpay/bizpayurl")){
			 wechat = true;
			}
		 System.out.println("ali:"+ali+" wechat:"+wechat);
	}

	public static void testNotify() {
		String url = "http://127.0.0.1:9080/syhd-pay-gateway/gatewayAction_payNotify.action?uid=4956021&amount=1&sn=141128166021758801&area=全国&ts=1417163642389&op=2&result=200&hash=E2D245024A2922F6D8C7B4D232B312BC&extra=4C9AF43236BA4B11B3BCB14E2C474054&trade_id=1411281660217588&ch_key=test_channel&app_key=10000000&goods_key=test1&mobile=13312341234&order_id=xy0820160401145949791471617589";
		String result = XiaoyHttpUtil.sendGet(url);
		System.out.println("result:" + result);
	}

	public static void testQuery() {
		String url = "http://127.0.0.1:9080/syhd-pay-gateway/gatewayAction_query.action";
		String sendJson = "{\"outType\":\"1\",\"outTradeNo\":\"1234567901\",\"appId\":\"APP00001007\",\"xiaoyTradeNo\":\"xy0820160401145950036222867789\",\"sign\":\"08\"}";
		String result = XiaoyHttpUtil.sendPost(url, sendJson, false);
		System.out.println("result:" + result);
	}

}

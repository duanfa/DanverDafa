package com.syhd.httpSender;

import com.syhd.util.XiaoyHttpUtil;

public class HeaderSender {
	public static void main(String[] args) {
		testInterface();
//		testGet();
	}

	public static void testInterface() {
		String url = "http://xiaoy.stvgame.com/wshouyou/homePickedAction_getHomePicked";
//		url = "http://localhost:9080/wshouyou/gameRemarkAction_save.action?gid=36c8f8ac61e443678324f345794ab0f3&uuid=37c8f8ac61e443678324f345794ab0f4&remarkTags=abc,def&content=content&score=1.0";
//		url = "http://localhost:9080/wshouyou/feedBackAction_submit.action?uuid=37c8f8ac61e443678324f345794ab0f4&content=%E4%BD%A0%E5%A5%BD&appId=1&content=content&channel=guan&contact=123";
//		url = "http://10.10.1.12:9080/wshouyou/homePickedAction_getHomePicked";
		//zhouyou
		String sendJson = "auth_no=F600314C5ACAD28D891502EE2DDC8500&imei=a739e25e1b02fac2c9d8f5d10fbc8856&sw=720&sh=1280&md=KIUI_Q2&manufacturer=Allwinner&os=android&platform=android&sdk=19&netType=ETHERNET&netExtra=&ver=3.0.8.0&verInt=3080&channel=guan&appId=1&cpu=ARMv7+Processor+rev+5+%28v7l%29+%2C8%2C1.8GHz%2C480.0MHz&gpu=&memory=782413824&rom=19&cpuHz=1800000&sdcardMemory=5165645824";
		//ZhuoZhuang box
		sendJson = "auth_no=F600314C5ACAD28D891502EE2DDC8500&imei=45f31d16b1058d586fc3be7207b58053&sw=1080&sh=1920&md=test&manufacturer=Hisilicon&os=android&platform=android&sdk=19&netType=WIFI&netExtra=&ver=3.0.9.0&verInt=3090&channel=ZhuoZhuang&appId=1&cpu=ARMv7+Processor+rev+5+%28v7l%29+%2C4%2C1.5GHz%2C600.0MHz&gpu=&memory=1027915776&rom=19&cpuHz=1500000&sdcardMemory=45334528";
		//baoyi
//		sendJson = "auth_no=F600314C5ACAD28D891502EE2DDC8500&imei=98ecb89e95293c11fae7629f60c8c561&sw=1080&sh=1920&md=M310&manufacturer=HUAWEI&os=android&platform=android&sdk=16&netType=WIFI&netExtra=&ver=3.0.7.0&verInt=3070&channel=guan&appId=1&cpu=ARMv7 Processor rev 0 (v7l) ,4,1.2GHz,200.0MHz&gpu=Immersion.16,Hisilicon Technologies,OpenGL ES-CM 1.1&memory=766644224&rom=16&cpuHz=1200000&sdcardMemory=2158731264";
		String result = XiaoyHttpUtil.sendHeadJsonPost(url, sendJson, false);
		System.out.println("result:" + result);
	}
	public static void testGet() {
		String url = "http://api.upay360.cn/api/payment/query?app_key=10001324&orderid=xy0320160331145940427218479337&hash=C5A3BC5500824715D604A83AA9968AA9";
		String result = XiaoyHttpUtil.sendGet(url);
		System.out.println("result:" + result);
	}

}


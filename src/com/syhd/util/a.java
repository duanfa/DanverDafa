package com.syhd.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

public class a {
	public static void main(String[] args) {
		String str = "{\"appCode\":\"7557\",\"mac\":\"bcec2377a401\",\"order_id\":\"0A5418245A174190AC31A0C0BF81DB9D\",\"pay_time\":\"2016-04-16 16:00:23\",\"resp_code\":\"0000\",\"sign_code\":\"2b1065852d44c9843c468ccca830b4d4\",\"tel\":\"2642108\"}";
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(validateSign(json,"89eaa1f26588427bc8a96923dd98b19f"));
	}

	private static boolean validateSign(JSONObject notityJson, String securetKey) {
		try {
			String sign_code = notityJson.getString("sign_code").trim();
			Map<String, String> paras = new HashMap<String, String>();
			Set<String> keys = notityJson.keySet();
			for (String key : keys) {
				paras.put(key, notityJson.getString(key));
			}
			paras.remove("sign_code");
			String parasStr = Coder.getSortValueString(paras);
			parasStr += securetKey;
			System.out.println("----------------------kukaiNotifier validateSign--------------------------------");
			System.out.println("sign_code:" + sign_code);
			System.out.println("parasStr:" + parasStr);
			System.out.println("securetKey:" + securetKey);
			System.out.println("myself sign:" + Coder.bytesToHexString(Coder.encryptMD5(parasStr.getBytes("utf-8"))));
			if (sign_code.equals(Coder.bytesToHexString(Coder.encryptMD5(parasStr.getBytes("utf-8"))))) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

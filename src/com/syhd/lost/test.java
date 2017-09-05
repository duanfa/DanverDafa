package com.syhd.lost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

public class test {
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("/home/socket/Desktop/164807.log")));
		String str = "";
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
		int sum2 = 0;
		while ((str = br.readLine()) != null) {
			sum2+=1;
			JSONObject json = JSONObject.fromObject(str.substring(27));
			String score = decimalFormat.format(json.getDouble("log10_score"));
			String key = score.substring(0, 4);
			Set<String> set = map.get(key);
			if (set == null) {
				set = new HashSet<String>();
			}
			set.add(json.getString("uid"));
			map.put(key, set);
		}
		int sum = 0;
		for (double i = 0.0; i < 1; i += 0.01) {
			String key = (i + "000").substring(0, 4);
			Set<String> set = map.get(key);
			if (set != null) {
				System.out.println(key + ":" + set.size());
				sum += set.size();
			} else {
				System.out.println(key + ":" + 0);
			}
		}
		
		
		int sum1 = 0;
		for(Entry<String, Set<String>> entry: map.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue().size());
			sum1 +=entry.getValue().size();
		}

		System.out.println(sum);
		System.out.println(sum1);
		System.out.println(sum2);
	}
}

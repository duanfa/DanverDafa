package com.syhd.insertSql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.syhd.util.DBCdapiUtil;
import com.syhd.util.DateUtil;
import com.syhd.util.MD5Util;

public class InsertMT2Pay {
	public static void main(String[] args) throws Exception{
		Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		Date endTime = DateUtil.day_sdf.parse(DateUtil.day_sdf.format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH) - 1);
		Date startTime = DateUtil.day_sdf.parse(DateUtil.day_sdf.format(calendar.getTime()));
		startTime = DateUtil.time_sdf.parse("2016-11-11 00:00:00");
		endTime = DateUtil.time_sdf.parse("2016-11-14 18:00:00");
		System.out.println("startTime:"+startTime);
		System.out.println("endTime:"+endTime);
		JSONArray jsonarray = getPayJsonArray(startTime,endTime);
		
		insertJsonArray(jsonarray);
	}

	private static JSONArray getPayJsonArray(Date startTime,Date endTime) throws NoSuchAlgorithmException {
		String timeStep = "BeginTime=" + DateUtil.time_sdf.format(startTime) + "&EndTime=" + DateUtil.time_sdf.format(endTime);
		String str = timeStep + "&Key=@$#sdfsdf82112178ysfasdfa@!$#@!4";
		String sign = MD5Util.md5(str);
		timeStep = timeStep.replaceAll(" ", "%20");
		String url = "http://59.151.93.27:8080/api/mt2tv/GetOrders.ashx?" + timeStep + "&UnixTime=" + System.currentTimeMillis() / 1000 + "&sign=" + sign;
		System.out.println("url:" + url);
		JSONObject json = JSONObject.fromObject(loadJson(url));
		JSONArray jsonarray = null;
		try {
			jsonarray = (JSONArray) json.get("orders");
		} catch (Exception e) {
			System.out.println(json);
			e.printStackTrace();
		}
		if(jsonarray.size()>999){
			Date middleTime = DateUtil.getMiddleTime(startTime, endTime) ;
			JSONArray preHalf = getPayJsonArray(startTime,middleTime);
			JSONArray postHalf = getPayJsonArray(middleTime,endTime);
			preHalf.addAll(postHalf);
			return preHalf;
		}else{
			return jsonarray;
		}
	}

	private static void insertJsonArray(JSONArray jsonarray) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBCdapiUtil.getConnection();
			String sql = "INSERT INTO mt2_pay ( `LogId`,`AppOrderId`,`ChannelOrderId` ,`Channel`,`AppId`,`UserName`,`ProductId`,`Money`,`RoleId`,`ChannelId`,`LogTime`,`GameId`,`Points`,`BonusPoints`,`Status` ,`CreateTime`,`ServerId`,`Ip`,`DeviceId`,`NotifyTime`,`Notes`,`Extra`,`xiaoy_appid`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'1001')";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int tmpCount = 0;
			for (Object j : jsonarray.toArray()) {
				JSONObject jj = (JSONObject) j;
				String NotifyTime = jj.get("NotifyTime").toString();
				if ("null".equals(NotifyTime)) {
					NotifyTime = "1970-00-00T00:00:00.001";
				}
				pstmt.setString(1, jj.get("LogId").toString());
				pstmt.setString(2, jj.get("AppOrderId").toString());
				pstmt.setString(3, jj.get("ChannelOrderId").toString());
				pstmt.setString(4, jj.get("Channel").toString());
				pstmt.setString(5, jj.get("AppId").toString());
				pstmt.setString(6, jj.get("UserName").toString());
				pstmt.setString(7, jj.get("ProductId").toString());
				pstmt.setString(8, jj.get("Money").toString());
				pstmt.setString(9, jj.get("RoleId").toString());
				pstmt.setString(10, jj.get("ChannelId").toString());
				pstmt.setString(11, jj.get("LogTime").toString());
				pstmt.setString(12, jj.get("GameId").toString());
				pstmt.setString(13, jj.get("Points").toString());
				pstmt.setString(14, jj.get("BonusPoints").toString());
				pstmt.setString(15, jj.get("Status").toString());
				pstmt.setString(16, jj.get("CreateTime").toString());
				pstmt.setString(17, jj.get("ServerId").toString());
				pstmt.setString(18, jj.get("Ip").toString());
				pstmt.setString(19, jj.get("DeviceId").toString());
				pstmt.setString(20, NotifyTime);
				pstmt.setString(21, jj.get("Notes").toString());
				pstmt.setString(22, jj.get("Extra").toString());
				pstmt.addBatch();
				try {
					pstmt.executeBatch();
					tmpCount += 1;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("intert finish " + tmpCount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}

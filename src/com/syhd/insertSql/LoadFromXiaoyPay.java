package com.syhd.insertSql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.syhd.util.DBCdapiUtil;
import com.syhd.util.RSACoder;

public class LoadFromXiaoyPay {
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		String endDate = sdf.format(calendar.getTime());
		String startDate = sdf.format(calendar.getTime());
		startDate = "2016-11-11";
		endDate = "2016-11-15";
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH) - 1);
		List<String> appNos = new ArrayList<String>();
		List<String> appIds = new ArrayList<String>();
		//-------------HT----------------------------------
		appNos.add("APP00001013");//HT
		appIds.add("1002");
		//-------------苍翼----------------------------------
		appNos.add("APP00001002");//苍翼
		appIds.add("1003");
		for(int i=0;i<appNos.size() ;i++){
			insertAppPay(appNos.get(i),appIds.get(i), startDate, endDate);
		}
	}

	private static void insertAppPay(String appNo,String appId, String startDate, String endDate) throws Exception, UnsupportedEncodingException, SQLException {

		String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALqUE0Uwx8CSAUKmXr7OKVXVfCEvigtVl1cehYnGrC40MGL4HOf7tl7f511J1i3vpt5vVoOyKyqKyPlayPOeLfXwsxaUFe1HawkpTdxlMSabt8z5m+bsws+vuDEBfFUrMKXXiO/s3YklgJ9gDnalIZbx9DqBPmEpS/c7Shne3PZVAgMBAAECgYEAqeJUHYcKCrymqyJDWKbtGjuVl73qiuF3OJsQ6l9cvYFFoYfbAhu+dWqYBuhJPXBd12E3PBjJlyXhUrKoELKxkxOZUeTZ8+bBLtQL9h5gcOw9lMJB5y4WyEfCCcG28Tj+eu9zO9wfmHEKhIVAeVnP0GutbPc0dpfLO6oKyFs7gS0CQQDxHNLwKGgTz2FpHS/cbW1/uwOuZXjER68ZDQXVRQwJ/r58zNQ35Mvimy7UXCfiTQduw7G4/84s3ZGyo5LHVUNnAkEAxhlACBwSm2sRZ7EPvpJ8I/pG0SY7xp3XPUIlZSRS7JMRvqQErkKSqI2qydIaWBJf+OrwOs3UCyR+ig9cpE7+4wJBAJ4/8tqZSqYl6KNa+rzMPaAmGKZwBzGccvoYKAnwRZSR1GiI74i37HGQtM3AppTLReOXIaBwlNuxmR0MaxvVhGUCQGlDMYsdn6WGFxGKPFt/aweTDd5E+q5nYN/kLgq4anfbDirG7NXL+jImVQI46layLN0PuecFSA9DrRmfYBLPBxECQQCHd6sxHWAzS5mn1lB6JlHFkCEvDQMPAKk1/RqqcHhq6mLWrOGPuC2lw9hOwMFyqZiP0gx+ygpjFaQ8egDphHwU";

		String needSing = "appNo=" + appNo + "&startDate=" + startDate + "&endDate=" + endDate;
		System.out.println(needSing);
		String sign = RSACoder.sign(needSing.getBytes(), privateKey);
		String xiaoySign = URLEncoder.encode(sign.replace("\n", ""), "UTF-8");
		String url = "http://123.57.157.199/syhd-pay-customer/orderAction_quereyOrder.action?" + needSing + "&sign=" + xiaoySign;
		System.out.println("url:" + url);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			int tmpCount = 0;
			JSONObject json = JSONObject.fromObject(loadJson(url));
			JSONArray jsonarray = (JSONArray) json.get("orders");
			conn = DBCdapiUtil.getConnection();
			String sql = "INSERT INTO xiaoy_pay (`outTradeNo`,`xiaoyTradeNo`,`appChannel`,`payChannel`,`AppNo`,`create_time`,`amount`,`xiaoy_appId` ) values (?,?,?,?,?,?,?,?) ;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Object j : jsonarray.toArray()) {
				try {
					JSONObject jj = (JSONObject) j;
					String outTradeNo = jj.getString("outTradeNo");
					Object appChannel = jj.get("appChannel");
					String payChannel = jj.getString("payChannel");
					String xiaoyTradeNo = jj.getString("xiaoyTradeNo");
					String AppNo = jj.getString("AppNo");
					String createTime = jj.getString("createTime");
					int amount = jj.getInt("amount");
					if(appChannel==null){
						appChannel = payChannel;
					}
					pstmt.setString(1, outTradeNo);
					pstmt.setString(2, xiaoyTradeNo);
					pstmt.setString(3, appChannel.toString());
					pstmt.setString(4, payChannel);
					pstmt.setString(5, AppNo);
					pstmt.setString(6, createTime);
					pstmt.setBigDecimal(7, new BigDecimal(amount));
					pstmt.setString(8, appId);
					pstmt.execute();
					tmpCount++;
				} catch (Exception e) {
					if(!e.getMessage().startsWith("Duplicate")){
						System.out.println(e.getMessage());
					}
				}
			}
			System.out.println("update:" + tmpCount);
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

package com.syhd.lost;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.syhd.util.DBCdapiUtil;

public class InsertNews {

	public static void main(String[] args) throws SQLException {
		insertImg();
	}

	private static void insertImg() throws SQLException {
		String preDir = "/home/syhd/exeFile/apache-tomcat-7.0.65/webapps";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBCdapiUtil.getConnection();
			String sql = "select imgPath,content from news";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				String imgPath = result.getString("imgPath");
				String content = result.getString("content");
				if (StringUtils.isNotBlank(imgPath)) {
					File file = new File(preDir + imgPath);
					if (!file.exists()) {
						String contentImg = getUrlByContent(content);
						if (StringUtils.isNotBlank(contentImg)) {
							String comand = " cp " + contentImg + " " + file.getAbsolutePath() + " ";
							
							String distPathDir =  file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("/"));
							if (!new File(distPathDir).exists()) {
								new File(distPathDir).mkdirs();
							}
							
							System.out.println(comand);
							execute(comand);
						}
					}
				}

			}
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

	private static String getUrlByContent(String content) {
		String preDir = "/home/syhd/exeFile/apache-tomcat-7.0.65/webapps";
		Pattern pattern = Pattern.compile("src=\".*?\"");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String url = matcher.group(0);
			url = url.substring(url.indexOf("/syhd_storage"), url.length() - 1);
			File file = new File(preDir + url);
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		}
		return "";
	}

	private static List<File> getSubFiles(String dir) {
		List<File> files = new ArrayList<File>();
		File fileDir = new File(dir);
		for (File file : fileDir.listFiles()) {
			if (file.isFile()) {
				files.add(file);
			} else {
				files.addAll(getSubFiles(file.getAbsolutePath()));
			}
		}
		return files;
	}

	private static String execute(String comand) {
		String result = "";
		try {
			String[] cmd = new String[] { "/bin/sh", "-c", " " + comand + " " };
			Process ps = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

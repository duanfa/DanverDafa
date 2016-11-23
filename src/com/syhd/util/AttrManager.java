package com.syhd.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class AttrManager {

	private Properties prop = null;

	public AttrManager(String confFile) {
		System.out.println("init conf file of : " + confFile);
		InputStream is = AttrManager.class.getClassLoader()
				.getResourceAsStream(confFile);
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getStr(String key) {
		Object value = getObj(key);
		String resultName = value.toString();
		return resultName;
	}

	public String getStr(String key, boolean convert) {
		Object value = getObj(key);
		String resultName = value.toString();
		if (convert) {
			try {
				resultName = new String(resultName.getBytes("ISO-8859-1"),
						"utf8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultName;
	}

	public Object getObj(String key) {
		Object value = prop.get(key);
		return value;
	}

	public int getInt(String key) {
		Object value = getObj(key);
		return Integer.parseInt(value.toString());
	}

	public boolean getBoolean(String key) {
		String value = getStr(key);
		return Boolean.parseBoolean(value);
	}

}

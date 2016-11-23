package com.syhd.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	public static String makeMD5Bak(String password) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(password.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public static String makeMD5(String password) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(password.getBytes());
			byte[] digest = md.digest();
			String result = new String(Hex.encodeHex(digest));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	// 检查是否是图片格式
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (StringUtils.isNotBlank(imgStr)) {
			if (imgStr.equalsIgnoreCase(".gif") || imgStr.equalsIgnoreCase(".jpg") || imgStr.equalsIgnoreCase(".jpeg") || imgStr.equalsIgnoreCase(".png")) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public static String encodingFileName(String fileName) {
		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(fileName, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			if (returnFileName.length() > 150) {
				returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
				returnFileName = StringUtils.replace(returnFileName, " ", "%20");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnFileName;
	}

	public static String getFormatSize(long size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte(s)";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	public static long getGBFormatSize(long size) {
		double kiloByte = size / 1024;
		double megaByte = kiloByte / 1024;

		double gigaByte = megaByte / 1024;

		// return Math.round(gigaByte);
		return (long) Math.floor(gigaByte);
	}

	public static String generateAbstractDir(String subDir) {
		return Constants.WEB_SITE_URL + subDir;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String EncodeUTF8(String str) {
		String res = null;
		if (StringUtils.isBlank(str)) {
			return "";
		}
		try {
			res = URLEncoder.encode(str, Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String DecodeUTF8(String str) {
		String res = null;
		if (StringUtils.isBlank(str)) {
			return "";
		}
		try {
			res = URLDecoder.decode(str, Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String ProcessJsonStr(String str) {
		String res = "";
		if (StringUtils.isNotBlank(str)) {
			res = EncodeUTF8(str);
		}
		return res;
	}

	public static String ProcessJsonStrNormal(String str) {
		if (StringUtils.isNotBlank(str)) {
			return str;
		}
		return "";
	}

	// 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	// 将十进制整数形式转换成127.0.0.1形式的ip地址
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}

	/**
	 * 从ip的字符串形式得到字节数组形式
	 * 
	 * @param ip
	 *            字符串形式的ip
	 * @return 字节数组形式的ip
	 */
	public static byte[] getIpByteArrayFromString(String ip) {
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		try {
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param ip
	 *            ip的字节数组形式
	 * @return 字符串形式的ip
	 */
	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}

	/**
	 * 根据某种编码方式将字节数组转换成字符串
	 * 
	 * @param b
	 *            字节数组
	 * @param offset
	 *            要转换的起始位置
	 * @param len
	 *            要转换的长度
	 * @param encoding
	 *            编码方式
	 * @return 如果encoding不支持，返回一个缺省编码的字符串
	 */
	public static String getString(byte[] b, int offset, int len, String encoding) {
		try {
			return new String(b, offset, len, encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(b, offset, len);
		}
	}

	// 获取网络图片
	public static boolean ImageRequest(String imageLocalFile, String picRemoteUrl) {
		FileOutputStream outStream = null;
		boolean flag = false;
		try {
			// new一个URL对象
			URL url = new URL(picRemoteUrl);
			// 打开链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			byte[] data = readInputStream(inStream);
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			File imageFile = new File(imageLocalFile);
			// 创建输出流
			outStream = new FileOutputStream(imageFile);
			// 写入数据
			outStream.write(data);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭输出流
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;

	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	/**
	 * 改进的32位FNV算法1
	 * 
	 * @param data
	 *            字符串
	 * @return int值
	 */
	public static int FNVHash1(String data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++)
			hash = (hash ^ data.charAt(i)) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	/**
	 * BKDR算法
	 */
	public static int BKDRHash(String str) {
		int seed = 131313; // 31 131 1313 13131 131313 etc..
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = (hash * seed) + str.charAt(i);
		}
		return (hash & 0x7FFFFFFF);
	}

	/* End Of BKDR Hash Function */

	/**
	 * SDBM算法
	 */
	public static int SDBMHash(String str) {
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		return (hash & 0x7FFFFFFF);
	}

	/* End Of SDBM Hash Function */

	/**
	 * DJB算法
	 */
	public static int DJBHash(String str) {
		int hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) + str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}

	/* End Of DJB Hash Function */

	/**
	 * DEK算法
	 */
	public static int DEKHash(String str) {
		int hash = str.length();

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}

	/* End Of DEK Hash Function */

	/**
	 * AP算法
	 */
	public static int APHash(String str) {
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash ^= ((i & 1) == 0) ? ((hash << 7) ^ str.charAt(i) ^ (hash >> 3)) : (~((hash << 11) ^ str.charAt(i) ^ (hash >> 5)));
		}

		// return (hash & 0x7FFFFFFF);
		return hash;
	}

	/**
	 * Bernstein's hash
	 * 
	 * @param key
	 *            输入字节数组
	 * @param level
	 *            初始hash常量
	 * @return 结果hash
	 */
	public static int bernstein(String key) {
		int hash = 0;
		int i;
		for (i = 0; i < key.length(); ++i)
			hash = 33 * hash + key.charAt(i);
		return hash;
	}


	public static BigDecimal GetBigDecimalFromInt(int val) {
		BigDecimal bv = new BigDecimal(val / 100.00).setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
		return bv;
	}

	public static int GetIntFromBigDecimal(BigDecimal val) {
		if (val != null) {
			return val.multiply(new BigDecimal(100)).intValue();
		}
		return 0;
	}

	public static void main(String[] args) {
		/*
		 * long l = 1024*1024*1024;
		 * System.out.println(BussinessUtil.processSdcardMemorySize
		 * (String.valueOf(l)));
		 * System.out.println(BussinessUtil.processMemorySize
		 * (String.valueOf(l)));
		 * System.out.println(CommonUtil.getGBFormatSize(l));
		 */
		// String str = "NCRELIwrZKVCgZE61439607832";
		String str = "NCRELIwrZKVCgZE61439609327";
		System.out.println(GetIntFromBigDecimal(new BigDecimal(0.01)));
	}
}

package com.syhd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * User: qinhw Date: 2015/12/02 Time: 16:04
 */
public class XiaoyHttpUtil {

	// 连接超时时间，默认10秒
	private int socketTimeout = 10000;

	// 传输超时时间，默认30秒
	private int connectTimeout = 30000;

	// 请求器的配置
	private static RequestConfig requestConfig;

	// HTTP请求器
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 通过Https往API post json数据
	 *
	 * @param url
	 *            API地址
	 * @param jsonObj
	 *            要提交的json数据对象
	 * @param needEncodeUTF8
	 *            是否需要URLEncode
	 * @return API回包的实际数据
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */

	public static String sendPost(String url, String jsonStr, boolean needEncodeUTF8) {
		String result = null;

		HttpPost httpPost = null;

		try {
			httpPost = new HttpPost(url);
			if (StringUtils.isNotBlank(jsonStr)) {
				if (needEncodeUTF8) {
					jsonStr = URLEncoder.encode(jsonStr, "UTF-8");
				}
				// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
				StringEntity postEntity = new StringEntity(jsonStr, "UTF-8");
				postEntity.setContentType("text/json");
				// httpPost.addHeader("Content-Type", "text/json");
				httpPost.setEntity(postEntity);
			}

			// 设置请求器的配置
			httpPost.setConfig(requestConfig);

			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}

		return result;
	}

	public static String sendGet(String url) {
		String result = null;
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			// 设置请求器的配置
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.abort();
			}
		}

		return result;
	}

	public static String sendHeadJsonPost(String url, String jsonStr, boolean needEncodeUTF8) {
		String result = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			if (StringUtils.isNotBlank(jsonStr)) {
				if (needEncodeUTF8) {
					jsonStr = URLEncoder.encode(jsonStr, "UTF-8");
				}
				httpPost.setHeader("head", jsonStr);
			}
			// 设置请求器的配置
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}

		return result;
	}

	/**
	 * 设置连接超时时间
	 *
	 * @param socketTimeout
	 *            连接时长，默认10秒
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		resetRequestConfig();
	}

	/**
	 * 设置传输超时时间
	 *
	 * @param connectTimeout
	 *            传输时长，默认30秒
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		resetRequestConfig();
	}

	private void resetRequestConfig() {
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}

	public static String sendKVPost(String url, Map<String, String> kvs) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : kvs.entrySet()) {
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("Response Code:" + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

}

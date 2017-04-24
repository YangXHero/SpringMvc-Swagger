package com.yyx.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class HttpTookitUtil {
	private static Log log = LogFactory.getLog(HttpTookitUtil.class);

	/*
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	public static String doPost(String url, Map<String, String> params) {

		return doPost(url, params, null, null);
	}

	public static String doPost(String url, Map<String, String> params,
			String body) {
		return doPost(url, params, body, null);
	}

	public static String doPost(String url, Map<String, String> params,
			String body, String filePath) {
		String response = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		if (StringUtils.isEmpty(filePath))
			method.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
		// 设置Http Post数据
		if (params != null) {
			int i = 0;
			Set<Entry<String, String>> sets = params.entrySet();

			NameValuePair[] paramsNameValuePairs = new NameValuePair[sets
					.size()];
			for (Map.Entry<String, String> entry : sets) {
				paramsNameValuePairs[i] = new NameValuePair(entry.getKey(),
						entry.getValue());
				i++;
				// method.addParameter(entry.getKey(), entry.getValue());
			}
			method.setQueryString(paramsNameValuePairs);
			// method.addParameter("data", params);
		}
		// if (StringUtils.isNotBlank(body))
		// method.setRequestBody(body);
		if (StringUtils.isNotEmpty(body)) {
			RequestEntity requestEntity;
			try {
				requestEntity = new StringRequestEntity(body, "text/json",
						"utf-8");
				method.setRequestEntity(requestEntity);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(filePath)) {
			try {

				File targetFile = new File(filePath);

				Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
				method.setRequestEntity(new MultipartRequestEntity(parts,
						method.getParams()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
				if (method.getResponseCharSet().equals("ISO-8859-1"))
					response = new String(response.getBytes("ISO-8859-1"),
							"utf-8");
			}
		} catch (IOException e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	public static String doPost(String url, String requestMethod,
			Map<String, String> params) {
		String response = null;
		url += requestMethod;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
		// 设置Http Post数据
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {

				method.addParameter(entry.getKey(), entry.getValue());
			}
			// method.addParameter("data", params);
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (IOException e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	public static String doGet(String url) {
		String response = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.addRequestHeader("Content-Type", "text/html;charset=UTF-8");

		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
				if (method.getResponseCharSet().equals("ISO-8859-1"))
					response = new String(response.getBytes("ISO-8859-1"),
							"utf-8");
			}
		} catch (IOException e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response;
	}

}
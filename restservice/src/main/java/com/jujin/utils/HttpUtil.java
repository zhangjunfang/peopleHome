package com.jujin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.jujin.common.OpResult;

/**
 * 
 * <p>
 * Title: HttpUtil.java
 * </p>
 * <p>
 * Description: HTTP工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: jujinziben
 * </p>
 * 
 * @author zhengshaoxu
 * @date 2015年12月4日
 * @version 1.0
 */
public class HttpUtil {
	
	public static String get(String url, Map<String, String> headMap,
			Map<String, String> paramMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		String param = URLEncodedUtils.format(params, "UTF-8");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url + "?" + param);
		for (Map.Entry<String, String> entry : headMap.entrySet()) {
			get.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse response;
		String result = "";
		try {
			response = httpclient.execute(get);
			int reCode = response.getStatusLine().getStatusCode();
			if (reCode == 200) {
				HttpEntity rspEntity = response.getEntity();
				if (rspEntity != null) {
					result = EntityUtils.toString(rspEntity, "utf-8");
				}
				System.out.println("返回代码：" + reCode);
			} else {
				HttpEntity rspEntity = response.getEntity();
				if (rspEntity != null) {
					result = EntityUtils.toString(rspEntity, "utf-8");
				}
				System.err.println("返回错误代码：" + reCode);
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static String post(String url, Map<String, String> headMap,
			Map<String, String> paramMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		for (Map.Entry<String, String> entry : headMap.entrySet()) {
			post.addHeader(entry.getKey(), entry.getValue());
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		CloseableHttpResponse response;
		String result = "";
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(post);
			int reCode = response.getStatusLine().getStatusCode();
			if (reCode == 200) {
				HttpEntity rspEntity = response.getEntity();
				if (rspEntity != null) {
					result = EntityUtils.toString(rspEntity, "utf-8");
				}
				System.out.println("返回代码：" + reCode);
			} else {
				HttpEntity rspEntity = response.getEntity();
				if (rspEntity != null) {
					result = EntityUtils.toString(rspEntity, "utf-8");
				}
				System.err.println("返回错误代码：" + reCode);
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String postEntity(String url,Object obj){
	    CloseableHttpClient httpclient = HttpClients.createDefault(); 
	    HttpPost post = new HttpPost(url);
		CloseableHttpResponse response;
		String result = "";
		try {
			post.addHeader("Content-type", "application/json");
			post.setEntity(new StringEntity(JSON.toJSONString(obj), "utf-8"));  
			response = httpclient.execute(post);
			int reCode = response.getStatusLine().getStatusCode();
			if(reCode == 200){
				HttpEntity rspEntity = response.getEntity();
			    if (rspEntity != null) {
			    	result = EntityUtils.toString(rspEntity,"utf-8");
			    }
			    System.out.println("返回代码："+reCode);
			}else{
				HttpEntity rspEntity = response.getEntity();
			    if (rspEntity != null) {
			    	result = EntityUtils.toString(rspEntity,"utf-8");
			    }
				System.err.println("返回错误代码："+reCode);
			}
		    response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

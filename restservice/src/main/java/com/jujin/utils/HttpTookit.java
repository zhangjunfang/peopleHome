package com.jujin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jujin.common.ExceptionHelper;

public class HttpTookit {

	// private static Log log = LogFactory.getLog(HttpTookit.class);
	protected static final Logger log = LoggerFactory.getLogger(HttpTookit.class);

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doGet(String url, String queryString, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (StringUtils.isNotBlank(queryString))
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (URIException e) {
			log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public static String doGet(String url) {

		return doGet(url, null, "UTF-8", false);
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	/*
	 * public static String doPost(String url, Map<String, String> params,
	 * String charset, boolean pretty) { StringBuffer response = new
	 * StringBuffer(); HttpClient client = new HttpClient(); HttpMethod method =
	 * new PostMethod(url); // 设置Http Post数据 if (params != null) {
	 * HttpMethodParams p = new HttpMethodParams(); for (Map.Entry<String,
	 * String> entry : params.entrySet()) { p.setParameter(entry.getKey(),
	 * entry.getValue()); } method.setParams(p); } try {
	 * client.executeMethod(method); if (method.getStatusCode() ==
	 * HttpStatus.SC_OK) { BufferedReader reader = new BufferedReader(new
	 * InputStreamReader(method.getResponseBodyAsStream(), charset)); String
	 * line; while ((line = reader.readLine()) != null) { if (pretty)
	 * response.append(line).append(System.getProperty("line.separator")); else
	 * response.append(line); } reader.close(); } } catch (IOException e) {
	 * log.error("执行HTTP Post请求" + url + "时，发生异常！", e); } finally {
	 * method.releaseConnection(); } return response.toString(); }
	 */
	public static String doPost(String urlPara, String content) throws Exception {
		StringBuffer buffer = new StringBuffer();
		try {
			String requestMethod = "POST";
			URL url = new URL(urlPara);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求的方式（GET/POst)
			httpUrlConn.setRequestMethod(requestMethod);
			httpUrlConn.connect();

			String outputStr = content;
			// 当有数据要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader iStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(iStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			iStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
			log.error(ExceptionHelper.getExceptionDetail(e));
		}

		return buffer.toString();

	}

	/*
	 * public static void main(String[] args) { String y =
	 * doGet("http://video.sina.com.cn/life/tips.html", null, "GBK", true);
	 * System.out.println(y); }
	 */
}
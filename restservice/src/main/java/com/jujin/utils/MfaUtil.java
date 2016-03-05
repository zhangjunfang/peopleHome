package com.jujin.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.jujin.common.OpResult;
import com.jujin.util.xglc.CommonUtil;

/**
 * Title: MfaUtil
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月24日
 */
public class MfaUtil {
	private final static String URL = "http://114.215.100.75:8080/api/notify/status";
	/**
	 * 标状态改变通知
	 */
	public final static String BORROW_STATUS_CHANGE = "1";
	/**
	 * 用户持标状态改变通知
	 */
	public final static String TENDER_STATUS_CHANGE = "2";
	/**
	 * 用户账户改变通知
	 */
	public final static String USER_ACCOUNT_CHANGE = "3";
	/**
	 * 用户绑卡/解卡操作通知
	 */
	public final static String BANKCARD_OPERATE_CHANGE = "4";
	/**
	 * 用户提现/充值操作通知
	 */
	public final static String CASHCHARGE_CHANGE = "5";
	/**
	 * 用户注册通知
	 */
	public final static String REGISTER = "6";
	/**
	 * 用户登录通知
	 */
	public final static String LOGIN = "7";
	/**
	 * 用户认证信息改变通知
	 */
	public final static String IDENTIFICATION = "8";
	
	
	/**
	 * 推送消息
	* Title: push
	* Description: 
	* @param type
	* @param obejct
	* @return
	 */
	public static OpResult push(String type,Object obejct) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("type", type);
		paramMap.put("date", CommonUtil.dateToString(new Date()));
		paramMap.put("content", JSON.toJSONString(obejct));
		OpResult result = post(URL, paramMap);
		return result;
	}
	
	

	@SuppressWarnings("deprecation")
	private static OpResult post(String url,Map<String,String> paramMap){
		OpResult rs = new OpResult();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
	    CloseableHttpClient httpclient = HttpClients.createDefault(); 
	    HttpPost post = new HttpPost(url);
	    List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	    Set<String> keySet = paramMap.keySet();  
	    for(String key : keySet) {  
	        nvps.add(new BasicNameValuePair(key, paramMap.get(key)));  
	    }  
		CloseableHttpResponse response;
		String result = "";
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
			response = httpclient.execute(post);
			int reCode = response.getStatusLine().getStatusCode();
			if(reCode == 200){
				HttpEntity rspEntity = response.getEntity();
			    if (rspEntity != null) {
			    	result = EntityUtils.toString(rspEntity,"utf-8");
			    	rs.setStatus(true);
			    }
			}else{
				HttpEntity rspEntity = response.getEntity();
			    if (rspEntity != null) {
			    	result = EntityUtils.toString(rspEntity,"utf-8");
			    	rs.setStatus(false);
					rs.setMsg("["+reCode+"]-"+result);
			    }
			}
		    response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}

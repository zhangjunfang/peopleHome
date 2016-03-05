package sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * Title: SignTest
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月3日
 */
public class SignTest {

	
	@Test
	public void testData() {
		String url = "http://localhost:8080/SpringMvcMaven/sign/get";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	@Test
	public void testSign() {
		String url = "http://localhost:8080/SpringMvcMaven/sign/sign";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	@Test
	public void testPage() {
		String url = "http://localhost:8080/SpringMvcMaven/sign/page";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		paramMap.put("pi", "1");
		paramMap.put("ps", "3");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	/**
	 * 获取接口返回内容
	* Title: getReString
	* Description: 
	* @param reqPkg
	* @return
	 */
	private static String getReString(String url,Map<String,String> paramMap){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		String param=URLEncodedUtils.format(params, "UTF-8");
		
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HttpGet get = new HttpGet(url+"?"+param);
		get.setHeader("appCode","xigualicai");
		get.setHeader("Content-Type","application/json;charset=UTF-8");
		CloseableHttpResponse response;
		String result = "";
		try {
			response = httpclient.execute(get);
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

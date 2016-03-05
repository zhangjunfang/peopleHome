package luckDraw;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.pro.common.model.ModelConfig;

/**
 * Title: AwardTest
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月7日
 */
public class AwardTest {

	@Test
	public void test() {
		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/test";
		Map<String,String> paramMap = new HashMap<String,String>();
		//String str = getReString(url,paramMap);
		//System.out.println(str);
		
		InputStream is= ModelConfig.class.getClass().getResourceAsStream("/models.xml");
		
		System.out.println(is);
	}
	
//	@Test
//	public void testSaveAwardConf() {
//		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/saveAwardConf";
//		Map<String,String> paramMap = new HashMap<String,String>();
//		List<AwardSet> awardPool = new ArrayList<AwardSet>();
//		awardPool.add(new AwardSet("1", "云南6天5晚双飞双人游", "3", "1",0.02));
//		awardPool.add(new AwardSet("2", "智能机器人扫地机", "2", "1",0.02));
//		awardPool.add(new AwardSet("3", "蚕丝被", "10", "1",0.06));
//		awardPool.add(new AwardSet("4", "聚金币", "-1", "-1",0.3));
//		awardPool.add(new AwardSet("5", "聚金券", "-1", "-1",0.3));
//		awardPool.add(new AwardSet("6", "现金红包", "-1", "-1",0.3));
//		paramMap.put("list", JSON.toJSONString(awardPool));
//		String str = getReString(url,paramMap);
//		System.out.println(str);
//	}
	
	@Test
	public void testPage() {
		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/page";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		paramMap.put("pi", "1");
		paramMap.put("ps", "2");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	@Test
	public void testData() {
		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/my";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	@Test
	public void testBig() {
		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/big";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
		String str = getReString(url,paramMap);
		System.out.println(str);
	}
	
	@Test
	public void testLuckDraw() {
		String url = "http://localhost:8080/SpringMvcMaven/luckDraw/draw";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", "xxbcoder");
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

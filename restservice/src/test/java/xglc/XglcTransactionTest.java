package xglc;
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

import com.jujin.util.xglc.CommonUtil;
import com.jujin.util.xglc.SignUtil;
import com.jujin.utils.HttpTookit;


/**
 * Title: XglcTransactionTest
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class XglcTransactionTest {

	/**
	 *西瓜在投资平台自动注册账户接口
	* Title: testRegistMember
	* Description:
	 */
	@Test
	public void test() {
		String url = "http://www.renrentou.com/rrtsso/getuserinfo";
		String appid = "rrt3NGIxVGBBvke8";
		String appsecret = "mY8gyBSRmCpMyBdxFtIQNAu3HSguahIN";
		String ticket = "ec3afjNXvR2St5gNE0KgA4vQoJWlfVkq5liJz4BaXXJtZLo6ZbwMRVRtSpXsg66qq1Y";
		String checkCode = SignUtil.encryptMD5(appid+appsecret+ticket);
		String queryString = "appid=rrt3NGIxVGBBvke8&checkCode="+checkCode+"&ticket="+ticket;
		String charset = "UTF-8";
		String s = HttpTookit.doGet(url, queryString, charset, false);
		System.out.println(s);
	}
	
	/**
	 *自动登录验证身份接口
	* Title: testRegistMember
	* Description:
	 */
	@Test
	public void testMemberCheck () {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/memberCheck";
		String userAccessKey = "ly";
		String paramKey = SignUtil.encryptAccessKey(userAccessKey);
		Map<String,String> map = new HashMap<String,String>();
		map.put("sign", SignUtil.encryptMD5(paramKey+SignUtil.signKey));
		map.put("userAccessKey", paramKey);
		String str = getReString(url, map);
		System.out.println(str);
	}
	
	
	
	/**
	 * 账户债权状态查询接口
	* Title: testAccountBenefitInfo
	* Description:
	 */
	@Test
	public void testAccountDebtInfo() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/accountDebtInfo";
		Map<String,String> map = new HashMap<String,String>();
		String userAccessKey = "ly";
		String orderIdList = "feitian_2015011400000000000000001276,feitian_2015070900000000000000001500,feitian_2015041700000000000000001332";
		String paramKey = SignUtil.encryptAccessKey(userAccessKey);
		System.out.println(SignUtil.encryptMD5(paramKey+orderIdList+SignUtil.signKey));
		map.put("sign", SignUtil.encryptMD5(paramKey+orderIdList+SignUtil.signKey));
		map.put("userAccessKey", paramKey);
		map.put("orderIdList", orderIdList);
		String str = getReString(url, map);
		System.out.println(str);
	}
	
	@Test
	public void testCreateSign() {
		String userAccessKey = "ly";
		String xgOrderSn = "XG10000210";
		String productCode = "2015070900000000000000001500";
		String returnUrl = "http://www.baidu.com";
		String sign = SignUtil.encryptMD5(userAccessKey+xgOrderSn+productCode+returnUrl+SignUtil.signKey);
		System.out.println(sign);
	}
	
	
	/**
	 * 账户订单详情查询接口
	* Title: testAccountBenefitInfo
	* Description:
	 */
	@Test
	public void testAccountOrderList() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/accountOrderList";
		String userAccessKey = "ly";
		String paramKey = SignUtil.encryptAccessKey(userAccessKey);
		String lastOrderTime = "2014-01-01 01:11:11";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userAccessKey", paramKey);
		map.put("lastOrderTime", lastOrderTime);
		map.put("sign", SignUtil.encryptMD5(paramKey+lastOrderTime+SignUtil.signKey));
		
		
		String str = getReString(url, map);
		System.out.println(str);
	}
	
	
	/**
	 * 账户订单详情查询接口
	* Title: testAccountBenefitInfo
	* Description:
	 */
	@Test
	public void testProcessTrade() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/processTrade";
		
		String userAccessKey = "ly";
		String paramKey = SignUtil.encryptAccessKey(userAccessKey);
		String xgOrderSn = "XG10000210";
		String productCode = "2015081400000000000000001578";
		String returnUrl = "www.xigualical.com/trade/returnUrl";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userAccessKey", "ly");
		map.put("xgOrderSn", xgOrderSn);
		map.put("productCode", productCode);
		map.put("returnUrl", returnUrl);
		String checkStr = "ly"+xgOrderSn+productCode+returnUrl;
		
		System.out.println(checkStr);
		map.put("sign", SignUtil.encryptMD5(checkStr+SignUtil.signKey));
		
		
		String str = getReString(url, map);
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

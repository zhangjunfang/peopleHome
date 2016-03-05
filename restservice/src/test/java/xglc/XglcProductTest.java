package xglc;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * Title: XglcTest
 * Description: 西瓜理财产品接口测试用例
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class XglcProductTest {

	/**
	 * 获取平台状态为预售、在售的产品列表
	* Title: testOnSaleProduct
	* Description:
	 */
	@Test
	public void testOnSaleProduct() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/onSaleProduct";
		String str = getReString(url);
		System.out.println(str);
	}

	/**
	 * 获取产品的当前更新信息
	* Title: testProductStateInfo
	* Description:
	 */
	@Test
	public void testProductStateInfo() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/productStateInfo?queryProductIdList=";
		String param = "2015090600000000000000001590,2015081400000000000000001578,2015081600000000000000001583";
		String str = getReString(url+param);
		System.out.println(str);
	}
	
	
	/**
	 * 获取平台下各个系列的全量付息方式和全量担保机构
	* Title: testSeriesInfo
	* Description:
	 */
	@Test
	public void testSeriesInfo() {
		String url = "http://localhost:8080/SpringMvcMaven/xglcApi/seriesInfo";
		String str = getReString(url);
		System.out.println(str);
	}
	
	
		
	/**
	 * 获取接口返回内容
	* Title: getReString
	* Description: 
	* @param reqPkg
	* @return
	 */
	private static String getReString(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HttpGet get = new HttpGet(url);
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
			}else{
				System.err.println("返回错误代码："+reCode);
			}
		    response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}

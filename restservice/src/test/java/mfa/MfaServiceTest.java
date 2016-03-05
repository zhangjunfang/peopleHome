//package mfa;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSON;
//import com.jujin.entity.account.RegisterEntity;
//import com.jujin.util.xglc.SignUtil;
//import com.jujin.utils.HttpTookit;
//
///**
// * Title: MfaServiceTest
// * Description: 
// * Company: jujinziben
// * @author zhengshaoxu
// * @date 2015年12月10日
// */
//public class MfaServiceTest {
//
//	@Test
//	public void testReg() {
//		RegisterEntity entity = new RegisterEntity();
//		entity.setAgree(true);
//		entity.setOpType("01");
//		entity.setPwd(pwd);
//		entity.setUserName("BSY-1501049903");
//		
//		
//		RegParam param = new RegParam();
//		param.setEmail("371178285@qq.com");
//		param.setIdentify("410182198910082117");
//		param.setName("BSY-1501049903");
//		param.setPhone("1501049903");
//		param.setToken("");
//			
//		String url = "http://localhost:8080/SpringMavenMvc/mfaservice/register";
//		String str = HttpTookit.doPost(url, JSON.toJSONString(entity));
//		System.out.println(str);
//	}
//	
//	
//
//}

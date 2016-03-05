package com.jujin.entity.pay.lianlian;

/**
 * 商户配置信息
 * 
 * @author guoyx e-mail:guoyx@lianlian.com
 * @date:2013-6-25 下午01:45:40
 * @version :1.0
 * 
 */
public interface PartnerConfig {

	// 银通公钥
	String YT_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
	// 商户私钥
	String TRADER_PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMlGNh/WsyZSYnQcHd9t5qUkhcOhuQmozrAY9DM4+7fhpbJenmYee4chREW4RB3m95+vsz9DqCq61/dIOoLK940/XmhKkuVjfPqHJpoyHJsHcMYy2bXCd2fI++rERdXtYm0Yj2lFbq1aEAckciutyVZcAIHQoZsFwF8l6oS6DmZRAgMBAAECgYAApq1+JN+nfBS9c2nVUzGvzxJvs5I5qcYhY7NGhySpT52NmijBA9A6e60Q3Ku7vQeICLV3uuxMVxZjwmQOEEIEvXqauyYUYTPgqGGcwYXQFVI7raHa0fNMfVWLMHgtTScoKVXRoU3re6HaXB2z5nUR//NE2OLdGCv0ApaJWEJMwQJBAPWoD/Cm/2LpZdfh7oXkCH+JQ9LoSWGpBDEKkTTzIqU9USNHOKjth9vWagsR55aAn2ImG+EPS+wa9xFTVDk/+WUCQQDRv8B/lYZD43KPi8AJuQxUzibDhpzqUrAcu5Xr3KMvcM4Us7QVzXqP7sFc7FJjZSTWgn3mQqJg1X0pqpdkQSB9AkBFs2jKbGe8BeM6rMVDwh7TKPxQhE4F4rHoxEnND0t+PPafnt6pt7O7oYu3Fl5yao5Oh+eTJQbyt/fwN4eHMuqtAkBx/ob+UCNyjhDbFxa9sgaTqJ7EsUpix6HTW9f1IirGQ8ac1bXQC6bKxvXsLLvyLSxCMRV/qUNa4Wxu0roI0KR5AkAZqsY48Uf/XsacJqRgIvwODstC03fgbml890R0LIdhnwAvE4sGnC9LKySRKmEMo8PuDhI0dTzaV0AbvXnsfDfp";

	// MD5 KEY
	String MD5_KEY = "201504201000291503";
	
	// 接收异步通知地址
	String NOTIFY_URL ="http://m.jujinziben.com/api/LianlianAsynchronousNoticeServlet";//"http://114.215.100.75/api/LianlianAsynchronousNoticeServlet"; //"http://m.jujinziben.com/api/LianlianAsynchronousNoticeServlet";
	
	
	// 支付结束后返回地址
	String URL_RETURN ="http://m.jujinziben.com/m/jujin"; //"http://114.215.100.75/m/jujin/";//"http://m.jujinziben.com/m/jujin/#/default";
	
	//支付结束后返回的移动端地址
	String URL_RETURN_MOBILE="http://m.jujinziben.com/m/jujin/success/index.html";
	
	// 商户编号
	String OID_PARTNER = "201504201000291503";
	// 签名方式 RSA或MD5
	String SIGN_TYPE = "MD5";
	// 接口版本号，固定1.0
	String VERSION = "1.1";

	// 业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售、108001：外部账户充值）

	String BUSI_PARTNER = "101001";// "108001";

	String APP_REQUEST = "3";

	String NAMES_GOODS = "聚金资本快捷充值";

}
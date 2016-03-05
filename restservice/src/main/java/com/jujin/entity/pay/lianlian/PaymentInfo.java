package com.jujin.entity.pay.lianlian;

import java.io.Serializable;


/**
* 支付信息bean
* @author guoyx
* @date:Jun 24, 2013 3:25:29 PM
* @version :1.0
*
*/
public class PaymentInfo extends MobilePaymentInfo{
	
	private String            version;              // 接口版本号
	
    private String            url_return;           // 支付结束回显url
    private String            app_request;          // 请求应用标识 1：Android 2：ios 3：WAP
    
    
    public String getApp_request()
    {
        return app_request;
    }

    public void setApp_request(String app_request)
    {
        this.app_request = app_request;
    }
    
    
    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getUrl_return()
    {
        return url_return;
    }

    public void setUrl_return(String url_return)
    {
        this.url_return = url_return;
    }
    

}
package com.jujin.controller.xglc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.controller.BaseController;

/**
 * Title: XglcApiController Description: 西瓜理财模拟器 Company: jujinziben
 * 
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
@Controller
@RequestMapping("/xglcMonitor")
public class XglcServiceController extends BaseController {

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public @ResponseBody
	Object regReturn(HttpServletRequest request,
			HttpServletResponse response) {
		String isRegisted = request.getParameter("isRegisted");
		String account = request.getParameter("account");
		String userAccessKey = request.getParameter("userAccessKey");
		String expireTime = request.getParameter("expireTime");
		String sign = request.getParameter("sign");
		String str = "isRegisted:"+isRegisted+",account:"+account+",userAccessKey:"+userAccessKey+",expireTime:"+expireTime+",sign:"+sign;
		System.out.println("XGLC-MONITOR-REG："+str);
		return str;
	}

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public @ResponseBody
	Object buyReturn(HttpServletRequest request,
			HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		String tradeResultStatus = request.getParameter("tradeResultStatus");
		String xgOrderSn = request.getParameter("xgOrderSn");
		String sign = request.getParameter("sign");
		String str = "orderId:"+orderId+",tradeResultStatus:"+tradeResultStatus+",xgOrderSn:"+xgOrderSn+",sign:"+sign;
		System.out.println("XGLC-MONITOR-BUY："+str);
		return str;
	}

}

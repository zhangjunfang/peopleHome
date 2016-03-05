/**   
 * @author wangning
 * @date 2015年2月12日 上午9:25:26 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jujin.authorize.AppSession;
import com.jujin.biz.AccountBiz;
import com.jujin.biz.ProductBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.account.AccountCenter;
import com.jujin.entity.borrow.BorrowInfo;

/**
 * 
 */
@Controller
public class ProductController extends BaseController {

	ProductBiz productBiz = new ProductBiz();

	@RequestMapping(value = "/product/{borrowid:\\d+}", method = RequestMethod.GET)
	public @ResponseBody OpEntityResult<BorrowInfo> getBorrowInfo(
			@PathVariable("borrowid") String borrowid) {
		logger.info(String.format("查询用户:[%s]中心数据", borrowid));

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (request != null) {
			logger.info("get request");
		} else {
			logger.info("can't get request");
		}

		OpEntityResult<BorrowInfo> info = productBiz.getInfo(borrowid);

		Object obj = request.getSession().getAttribute(
				SystemConfig.USERNAME_KEY);
		if (obj != null) {
			logger.info(((AppSession) obj).getActiveTime());
			info.setMsg(((AppSession) obj).getActiveTime().toString());
		} else {
			logger.info("can't get USERNAME_KEY");
		}

		return info;
	}

	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Object getBorrowInfo(String userid, String pwd) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (request != null) {
			logger.info("get request");
		} else {
			logger.info("can't get request");
		}
		AppSession session = new AppSession();
		request.getSession().setAttribute(SystemConfig.USERNAME_KEY, session);

		return request.getSession().getAttribute(SystemConfig.USERNAME_KEY);
	}*/

}

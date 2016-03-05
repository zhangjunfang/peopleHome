package com.jujin.controller.xglc;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jujin.biz.xglc.XglcProductBiz;
import com.jujin.biz.xglc.XglcTransactionBiz;
import com.jujin.common.Constants;
import com.jujin.common.OpEntityResult;
import com.jujin.controller.BaseController;
import com.jujin.entity.xglc.borrow.BorrowInfoDTO;
import com.jujin.entity.xglc.borrow.BorrowSeriesDTO;
import com.jujin.entity.xglc.borrow.BorrowUpdateInfoDTO;
import com.jujin.entity.xglc.borrow.DataTemplateDTO;
import com.jujin.entity.xglc.transaction.DebtInfoDTO;
import com.jujin.entity.xglc.transaction.DebtTemplateDTO;
import com.jujin.entity.xglc.transaction.OrderInfoDTO;
import com.jujin.entity.xglc.transaction.OrderTemplateDTO;
import com.jujin.entity.xglc.transaction.RecoverDTO;
import com.jujin.entity.xglc.transaction.XglcExcBean;
import com.jujin.util.xglc.SignUtil;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.StringUtils;
import com.wicket.loan.web.login.bean.LoginBean;
import com.wicket.loan.web.login.mediator.LoginMediator;

/**
 * Title: XglcApiController Description: 对外开放接口(西瓜理财) Company: jujinziben
 * 
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
@Controller
@RequestMapping("/xglcApi")
public class XglcApiController extends BaseController {

	XglcProductBiz proMediator = new XglcProductBiz();
	XglcTransactionBiz traMediator = new XglcTransactionBiz();
	LoginMediator loginMediator = new LoginMediator();
	
	String basePath = "http://m.jujinziben.com/m/jujin/#/";
	/********************************* 产品接口 ***********************************/
	/**
	 * Title: onSaleProduct Description: 获取平台状态为预售、在售的产品列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/onSaleProduct", method = RequestMethod.GET)
	public @ResponseBody
	Object onSaleProduct(HttpServletRequest request,
			HttpServletResponse response) {
		List<BorrowInfoDTO> list = proMediator.getSaleProduct();
		DataTemplateDTO dto = new DataTemplateDTO(list);
		return dto;
	}

	/**
	 * 
	 * Title: onSaleProduct Description: 获取产品的当前更新信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/productStateInfo", method = RequestMethod.GET)
	public @ResponseBody
	Object productStateInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String queryProductIdList = request.getParameter("queryProductIdList");
		if(StringUtils.isEmpty(queryProductIdList)){
			return "请检查参数，缺少queryProductIdList";
		}
		List<BorrowUpdateInfoDTO> list = proMediator
				.getProductStateInfo(queryProductIdList);
		DataTemplateDTO dto = new DataTemplateDTO(list);
		return dto;
	}

	/**
	 * 
	 * Title: onSaleProduct Description: 获取平台下各个系列的全量付息方式和全量担保机构
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/seriesInfo", method = RequestMethod.GET)
	public @ResponseBody
	Object seriesInfo(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath(
				"/WEB-INF/classes/seriesInfo.json");
		List<BorrowSeriesDTO> list = proMediator.getSeriesInfo(path);
		DataTemplateDTO dto = new DataTemplateDTO(list);
		return dto;
	}

	/********************************* 交易接口 ***********************************/

	/**
	 * 
	 * Title: loginMember Description: 西瓜在平台登录\注册账户的界面URL
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginMember", method = RequestMethod.GET)
	public void loginMember(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");
			
			String returnUrl = request.getParameter("returnUrl");
			String account = request.getParameter("account");
			String sign = request.getParameter("sign");
			
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(returnUrl)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数returnUrl");
				retJsonStr(response, exc);
				return;
			}
			if(account == null){
				account = "";
			}
			String checkStr = account + URLDecoder.decode(returnUrl,"utf-8");
			// 验证签名
			if (!SignUtil.checkSign(sign, checkStr)) {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				retJsonStr(response, exc);
			} 
			String url = basePath+"xgmed?channel=xglc&account="+account+"&returnUrl="+URLDecoder.decode(returnUrl,"utf-8");
			pageRedirect(response, url);

		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			retJsonStr(response, exc);
		}
	}
	

	/**
	 * 
	 * Title: registMember Description: 自动登录验证身份接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberCheck ", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object memberCheck(HttpServletRequest request, HttpServletResponse response) {
		Boolean boo = false;
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");
			String paramUserKey = request.getParameter("userAccessKey");
			String sign = request.getParameter("sign");
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				return exc;
			}
			if(StringUtils.isEmpty(paramUserKey)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数userAccessKey");
				return exc;
			}
			String userAccessKey = SignUtil.decryptAccessKey(paramUserKey);
			// 验证签名
			if (sign != null && SignUtil.checkSign(sign, userAccessKey)) {
				// 获取该用户,生成登录参数
				LoginBean loginBean = traMediator
						.getLoginBeanByAccessKey(userAccessKey);
				// 登录用户
				if (loginBean != null) {
					// 为了使用原有的登录，只能将加密过的密码先解密╮(╯▽╰)╭
					String pwd = DesCodeUtil.decrypt(loginBean.getUserPwd());
					loginBean.setUserPwd(pwd);
					loginBean.setLoginIp(getIpAddr(request));

					String renStr = loginMediator.isLogin(loginBean,
							request.getSession());

					if (StringUtils.isEmpty(renStr)) {
						boo = true;
					}
				}
				Map<String, Boolean> map = new HashMap<String, Boolean>();
				map.put("isLoginSucceed", boo);
				return map;
			} else {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				return exc;
			}
		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			return exc;
		}
	}

	/**
	 * 
	 * Title: accountDebtInfo Description: 进入产品购买界面URL
	 * 1.跳转到产品详情页，增加购买标示channel=xglc 2.购买成功后，根据channel标示来判断是否推送购买结果
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/processTrade", method = RequestMethod.GET)
	public void processTrade(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");

			String sign = request.getParameter("sign");

			String paramUserKey = request.getParameter("userAccessKey");
			String xgOrderSn = request.getParameter("xgOrderSn");
			String productCode = request.getParameter("productCode");
			String returnUrl = request.getParameter("returnUrl");

			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(paramUserKey)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数userAccessKey");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(xgOrderSn)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数xgOrderSn");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(productCode)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数productCode");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(returnUrl)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数returnUrl");
				retJsonStr(response, exc);
				return;
			}
			String userAccessKey = SignUtil.decryptAccessKey(paramUserKey);

			// 获取该用户,生成登录参数
			LoginBean loginBean = traMediator.getLoginBeanByAccessKey(userAccessKey);
			// 登录用户
			if (loginBean != null) {
				// 为了使用原有的登录，只能将加密过的密码先解密╮(╯▽╰)╭
				String pwd = DesCodeUtil.decrypt(loginBean.getUserPwd());
				loginBean.setUserPwd(pwd);
				loginBean.setLoginIp(getIpAddr(request));

				String renStr = loginMediator.isLogin(loginBean,request.getSession());

				if (!StringUtils.isEmpty(renStr)) {
					XglcExcBean exc = new XglcExcBean("请求失败", "投标登录授权失败");
					retJsonStr(response, exc);
					return;
				}
			}
			

			String checkStr = productCode+returnUrl+userAccessKey+xgOrderSn ;
			// 验证签名
			if (!SignUtil.checkSign(sign, checkStr)) {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				retJsonStr(response, exc);
			} 
			String url = basePath+"xglcdisbor?productCode="+productCode+"&channel=xglc&userAccessKey="+paramUserKey+"&xgOrderSn="+xgOrderSn+"&returnUrl="+URLDecoder.decode(returnUrl,"utf-8");
			pageRedirect(response, url);
			
		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			retJsonStr(response, exc);
		}
	}

	/**
	 * 
	 * Title: accountDebtInfo Description: 账户债权状态查询接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountDebtInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object accountDebtInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");

			String sign = request.getParameter("sign");
			String orderIdList = request.getParameter("orderIdList");
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				return exc;
			}
			if(StringUtils.isEmpty(orderIdList)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数orderIdList");
				return exc;
			}
			
			String checkStr = orderIdList;
			// 验证签名
			if (sign != null && SignUtil.checkSign(sign, checkStr)) {
				List<DebtInfoDTO> list = traMediator.getAccountDebtInfoList(orderIdList);
				DebtTemplateDTO dto = new DebtTemplateDTO(list);
				return dto;
			} else {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				return exc;
			}
		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			return exc;
		}
	}

	/**
	 * 
	 * Title: accountOrderList Description: 账户订单详情查询接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountOrderList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object accountOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");

			String sign = request.getParameter("sign");
			String paramUserKey = request.getParameter("userAccessKey");
			String lastOrderTime = request.getParameter("lastOrderTime");
			String pageIndex = request.getParameter("pageIndex");
			String pageSize = request.getParameter("pageSize");

			
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				return exc;
			}
			if(StringUtils.isEmpty(paramUserKey)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数userAccessKey");
				return exc;
			}
			if(StringUtils.isEmpty(lastOrderTime)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数lastOrderTime");
				return exc;
			}
			
			String userAccessKey = SignUtil.decryptAccessKey(paramUserKey);
			String checkStr = "";
			checkStr = checkStr + URLDecoder.decode(lastOrderTime,"utf-8");
			if(StringUtils.isEmpty(pageIndex)){
				pageIndex = "1";
			}else{
				checkStr = checkStr + pageIndex; 
			}
			
			if(StringUtils.isEmpty(pageSize)){
				pageSize = "10";
			}else{
				checkStr = checkStr + pageSize; 
			}
			checkStr = checkStr + userAccessKey;
			// 验证签名
			if (sign != null && SignUtil.checkSign(sign, checkStr)) {
				OrderTemplateDTO dto = new OrderTemplateDTO();
				List<OrderInfoDTO> orderList = traMediator.getAccountOrderList(userAccessKey, URLDecoder.decode(lastOrderTime,"utf-8"),pageIndex,pageSize,dto);
				for (OrderInfoDTO order : orderList) {
					List<RecoverDTO> recoverList = traMediator.getOrderRecoverList(order.getOrderId());
					order.setRecoverList(recoverList);
				}
				dto.setOrderCount(orderList.size());
				dto.setOrderList(orderList);
				return dto;
			} else {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				return exc;
			}
		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			return exc;
		}
	}

	@RequestMapping(value = "/xglcqueryuser", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object QueryXglcUser(String id, HttpServletRequest request,
			HttpServletResponse response) {

		OpEntityResult<String> result = new OpEntityResult<String>(null);
		try {

			if (StringUtils.isEmpty(id)) {
				result.setStatus(false);
				result.setMsg("错误的数据类型");
				return result;
			}
			String useridentity = traMediator.QueryXglcUser(id);// 可能为用户名或者为用户手机号,如果用户在移动端用手机号注册,那么他的用户名为手机_,为了对用户侧不产生岐义,显示为用户名
			result.setStatus(true);
			result.setEntity(useridentity);// 如果为"",说明为新用户需要注册
			
		} catch (Exception e) {
			result.setStatus(false);
			result.setMsg("校验数据用户名失败，请重试");
		}
		return result;

	}
	
	/**西瓜理财购买页面跳转
	 * 根据不同标类型显示相应的页面,并把相关参数加入session
	 * 在用户投资成功的时候返回（每次投标都需要调用此接口）
	 * 
	 * **/
	@RequestMapping(value = "/xglcdisbor", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object XglcDispatcherBorrow(String productCode,String channel,
			String userAccessKey,
			String xgOrderSn,
			String returnUrl,HttpServletRequest request,
			HttpServletResponse response) {

		OpEntityResult<String> result = new OpEntityResult<String>(null);
		try {

			if (StringUtils.isEmpty(productCode)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少productCode");
				return result;
			}
			if (StringUtils.isEmpty(channel)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少channel");
				return result;
			}
			if (StringUtils.isEmpty(userAccessKey)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少userAccessKey");
				return result;
			}
			if (StringUtils.isEmpty(xgOrderSn)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少xgOrderSn");
				return result;
			}
			if (StringUtils.isEmpty(returnUrl)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少returnUrl");
				return result;
			}
			HttpSession session=request.getSession();
			
			if(null!=session)
			{
				session.setAttribute(Constants.XGLC_RPODUCT_CODE, productCode);
				session.setAttribute(Constants.XGLC_USER_ACCESS_KEY, userAccessKey);
				session.setAttribute(Constants.XGLC_XG_ORDER_SN, xgOrderSn);
				session.setAttribute(Constants.XGLC_RETURN_URL, returnUrl);
			}
			String url = traMediator.QueryXglcBorrow(productCode);// 可能为用户名或者为用户手机号,如果用户在移动端用手机号注册,那么他的用户名为手机_,为了对用户侧不产生岐义,显示为用户名
			if(!StringUtils.isEmpty(url))
			{
				result.setStatus(true);
				result.setEntity(url);// 如果为"",说明为新用户需要注册
			}
			else
			{
				result.setStatus(false);
				result.setMsg(String.format("不存在产品编号为:[%s]的产品",productCode));
			}
		} catch (Exception e) {
			result.setStatus(false);
			result.setMsg("校验数据用户名失败，请重试");
		}
		return result;
	}
	
	/** 
	 * 根据不同标类型显示相应的页面 
	 * **/
	@RequestMapping(value = "/disbor", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object DispatcherBorrow(String id, HttpServletRequest request,
			HttpServletResponse response) {

		OpEntityResult<String> result = new OpEntityResult<String>(null);
		try {

			if (StringUtils.isEmpty(id)) {
				result.setStatus(false);
				result.setMsg("请检查参数，缺少id");
				return result;
			}
			String url = traMediator.QueryXglcBorrow(id);// 可能为用户名或者为用户手机号,如果用户在移动端用手机号注册,那么他的用户名为手机_,为了对用户侧不产生岐义,显示为用户名
			if(!StringUtils.isEmpty(url))
			{
				result.setStatus(true);
				result.setEntity(url);// 如果为"",说明为新用户需要注册
			}
			else
			{
				result.setStatus(false);
				result.setMsg(String.format("不存在产品编号为:[%s]的产品",id));
			}
		} catch (Exception e) {
			result.setStatus(false);
			result.setMsg("校验数据用户名失败，请重试");
		}
		return result;
	}
	
	/**
	 * 跳转到充值页面URL
	* Title: toRecharge
	* Description: 
	* @param id
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/toRecharge", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public void toRecharge(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");
			
			String paramKey = request.getParameter("userAccessKey");
			String sign = request.getParameter("sign");
			if(StringUtils.isEmpty(paramKey)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数userAccessKey");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				retJsonStr(response, exc);
				return;
			}
			String userAccessKey = SignUtil.decryptAccessKey(paramKey);
			String checkStr = userAccessKey;
			// 验证签名
			if (!SignUtil.checkSign(sign, checkStr)) {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				retJsonStr(response, exc);
			} 
			
			// 获取该用户,生成登录参数
			LoginBean loginBean = traMediator.getLoginBeanByAccessKey(userAccessKey);
			// 登录用户
			if (loginBean != null) {
				logger.info("执行充值前登录");
				// 为了使用原有的登录，只能将加密过的密码先解密╮(╯▽╰)╭
				String pwd = DesCodeUtil.decrypt(loginBean.getUserPwd());
				loginBean.setUserPwd(pwd);
				loginBean.setLoginIp(getIpAddr(request));

				String renStr = loginMediator.isLogin(loginBean,request.getSession());

				if (!StringUtils.isEmpty(renStr)) {
					XglcExcBean exc = new XglcExcBean("请求失败", "投标登录授权失败");
					retJsonStr(response, exc);
					return;
				}
			}
			
			String url = basePath+"default";
			pageRedirect(response, url);

		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			retJsonStr(response, exc);
		}
	}
	
	
	
	/**
	 * 跳转到提现页面URL
	* Title: toWithdraw
	* Description: 
	* @param id
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/toWithdraw", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public void toWithdraw(String id, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.setHeader("appCode", "xigualicai");
			
			String paramKey = request.getParameter("userAccessKey");
			String sign = request.getParameter("sign");
			if(StringUtils.isEmpty(paramKey)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数userAccessKey");
				retJsonStr(response, exc);
				return;
			}
			if(StringUtils.isEmpty(sign)){
				XglcExcBean exc = new XglcExcBean("请求失败", "缺少参数sign");
				retJsonStr(response, exc);
				return;
			}
			String userAccessKey = SignUtil.decryptAccessKey(paramKey);
			String checkStr = userAccessKey;
			// 验证签名
			if (!SignUtil.checkSign(sign, checkStr)) {
				XglcExcBean exc = new XglcExcBean("请求失败", "签名验证失败");
				retJsonStr(response, exc);
			} 
			
			// 获取该用户,生成登录参数
			LoginBean loginBean = traMediator.getLoginBeanByAccessKey(userAccessKey);
			// 登录用户
			if (loginBean != null) {
				logger.info("执行提现前登录");
				// 为了使用原有的登录，只能将加密过的密码先解密╮(╯▽╰)╭
				String pwd = DesCodeUtil.decrypt(loginBean.getUserPwd());
				loginBean.setUserPwd(pwd);
				loginBean.setLoginIp(getIpAddr(request));

				String renStr = loginMediator.isLogin(loginBean,request.getSession());

				if (!StringUtils.isEmpty(renStr)) {
					XglcExcBean exc = new XglcExcBean("请求失败", "投标登录授权失败");
					retJsonStr(response, exc);
					return;
				}
			}
			
			String url = basePath+"default";
			pageRedirect(response, url);

		} catch (Exception e) {
			response.setStatus(500);
			XglcExcBean exc = new XglcExcBean("服务端异常", "服务端处理业务出现异常");
			retJsonStr(response, exc);
		}
	}
	
	
	
	
	private void retJsonStr(HttpServletResponse response,Object obj){
		  try {
			response.getWriter().print(JSON.toJSONString(obj));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void pageRedirect(HttpServletResponse response,String url){
		  try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}

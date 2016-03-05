/**   
 * @author wangning
 * @date 2015年2月12日 下午4:28:25 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.authorize;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;

public class AuthorizedInterceptor implements HandlerInterceptor {

	protected static final Logger logger = Logger
			.getLogger(BaseController.class);
	
	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.info("afterCompletion:  "+arg0.getRequestURI());
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
	 * ，也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
	 * ，这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法
	 * ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前
	 * ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle:  "+request.getRequestURI());
	}

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
	 * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		logger.info("preHandle:  "+uri);
		// 设置不拦截的对象
		String[] noFilters = new String[] { "login", "index", "borrow" }; // 对登录本身的页面以及业务不拦截
		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}

		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder
		// .getRequestAttributes()).getRequest();

		AppSession session = (AppSession) request.getSession().getAttribute(
				SystemConfig.USERNAME_KEY);
		if (session != null) {
			return false;
		}
		return true;
	}

	/*
	 * @RequestMapping(value="/index") public String index(HttpServletRequest
	 * httpRequest,HttpServletResponse httpResponse) throws IOException{ String
	 * user_name = httpRequest.getParameter("user_name"); String user_pwd =
	 * httpRequest.getParameter("user_pwd"); String str = null; UtcUsersDao
	 * usersDao = new UtcUsersDao(); UtcUsers users =
	 * usersDao.findByNameAndPwd(user_name,user_pwd); if(users==null){//登录验证失败
	 * logger.info("登录失败");
	 * httpRequest.getSession().setAttribute("errorInfo","用户名或密码错误，请重新登录！");
	 * String path = httpRequest.getContextPath(); String basePath =
	 * httpRequest.getScheme() + "://"+ httpRequest.getServerName() + ":" +
	 * httpRequest.getServerPort()+ path + "/";
	 * httpResponse.sendRedirect(basePath+"self/logOn.do"); }else if
	 * ("10".equals(users.getUserrole())) { int loginMaxAge = 30*24*60*60;
	 * //定义账户密码的生命周期，这里是一个月。单位为秒 String rememberPwd =
	 * httpRequest.getParameter("rememberPwd"
	 * )==null?"":httpRequest.getParameter("rememberPwd").toString(); if
	 * ("rememberPwd".equals(rememberPwd)) { CookieTool.addCookie(httpResponse ,
	 * "loginName" , user_name , loginMaxAge); //将姓名加入到cookie中
	 * CookieTool.addCookie(httpResponse , "loginPwd" , user_pwd , loginMaxAge);
	 * //将密码加入到cookie中 } httpRequest.getSession().setAttribute("utcUsers",
	 * users); str = "/Administrator"; }else { int loginMaxAge = 30*24*60*60;
	 * //定义账户密码的生命周期，这里是一个月。单位为秒 String rememberPwd =
	 * httpRequest.getParameter("rememberPwd"
	 * )==null?"":httpRequest.getParameter("rememberPwd").toString(); if
	 * ("rememberPwd".equals(rememberPwd)) { CookieTool.addCookie(httpResponse ,
	 * "loginName" , user_name , loginMaxAge); //将姓名加入到cookie中
	 * CookieTool.addCookie(httpResponse , "loginPwd" , user_pwd , loginMaxAge);
	 * //将密码加入到cookie中 } //将UtcUsers放到session中
	 * httpRequest.getSession().setAttribute("utcUsers", users); str =
	 * "self/index"; } return str; }
	 */

}
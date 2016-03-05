package com.ocean.springmvc.core.handler.authentication.method;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ocean.springmvc.core.constant.Constant;
public class MethodAuthorityInterceptor implements HandlerInterceptor {

	private List<MethodAuthentication> authentication;
	
	public List<MethodAuthentication> getAuthentication() {
		return authentication;
	}

	public void setAuthentication(List<MethodAuthentication> authentication) {
		synchronized (this) {
			Collections.sort(authentication,
					new Comparator<MethodAuthentication>() {

						@Override
						public int compare(MethodAuthentication o1,
								MethodAuthentication o2) {

							if (o1.getOrder() > o2.getOrder()) {
								return 1;
							} else if (o1.getOrder() < o2.getOrder()) {
								return -1;
							}
							return 0;
						}
					});
			this.authentication = authentication;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			if (null != authentication) {
				for (MethodAuthentication authentication : getAuthentication()) {
					if (!authentication.isAuthentication(request.getSession()
							.getAttribute(Constant.SUBJECT),((HandlerMethod)handler).getMethod())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	
}

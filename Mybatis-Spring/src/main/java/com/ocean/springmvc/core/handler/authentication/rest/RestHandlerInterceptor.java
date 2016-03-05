package com.ocean.springmvc.core.handler.authentication.rest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ocean.springmvc.core.constant.Constant;

public class RestHandlerInterceptor implements HandlerInterceptor {

	private List<RestAuthentication> authentication;

	public List<RestAuthentication> getAuthentication() {

		return authentication;
	}

	public void setAuthentication(List<RestAuthentication> authentication) {
		synchronized (this) {
			Collections.sort(authentication,
					new Comparator<RestAuthentication>() {

						@Override
						public int compare(RestAuthentication o1,
								RestAuthentication o2) {

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
		// String className = handler.getClass().getName();
		// DefaultServletHttpRequestHandler defaultServletHttpRequestHandler;1
		// ResourceHttpRequestHandler httpRequestHandler;2
		// HandlerMethod handler; 3
		if (handler instanceof HandlerMethod) {
			if (null != authentication) {
				for (RestAuthentication authentication : getAuthentication()) {
					if (!authentication.isAuthentication(request.getSession()
							.getAttribute(Constant.SUBJECT), request
							.getRequestURI())) {
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

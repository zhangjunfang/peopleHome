package com.ocean.session.sessionCluster.rest.mvc;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ocean.session.sessionCluster.rest.HttpSessionConfig;
import com.ocean.session.sessionCluster.rest.SecurityConfig;


/**
 * @author Rob Winch
 */
public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfig.class, HttpSessionConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}

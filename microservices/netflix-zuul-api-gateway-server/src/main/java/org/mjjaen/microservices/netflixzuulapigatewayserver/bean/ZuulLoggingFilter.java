package org.mjjaen.microservices.netflixzuulapigatewayserver.bean;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ZuulLoggingFilter extends ZuulFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object run() {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {} request uri -> {} request port -> {}", request, request.getRequestURI(), request.getLocalPort());
		return null;
	}
	
	@Override
	public boolean shouldFilter() {
		return true; //All the requests
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	@Override
	public String filterType() {
		return "pre";
	}
}

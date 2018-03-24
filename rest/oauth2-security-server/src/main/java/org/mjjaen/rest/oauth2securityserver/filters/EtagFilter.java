package org.mjjaen.rest.oauth2securityserver.filters;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagFilter {
	/*
	 * Apply to all requests
	 */
	@Bean
	public Filter filter() {
		ShallowEtagHeaderFilter filter = new ShallowEtagHeaderFilter();
		return filter;
	}
}

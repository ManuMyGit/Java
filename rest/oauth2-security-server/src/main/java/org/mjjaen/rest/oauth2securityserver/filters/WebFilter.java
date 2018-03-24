package org.mjjaen.rest.oauth2securityserver.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*@Component
@Order(Ordered.HIGHEST_PRECEDENCE)*/
public class WebFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Initiating WebFilter >> ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
		String remote_addr = request.getRemoteAddr();
		requestWrapper.addHeader("remote_addr", remote_addr);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
		System.out.println("Destroying WebFilter >> ");
	}
	
	private class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
		private Map<String, String> headerMap = new HashMap<String, String>();
		
		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		public void addHeader(String name, String value) {
	        headerMap.put(name, value);
	    }
		@Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }
		
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }
        
        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }
	}
}

package org.mjjaen.rest.oauth2securityserver.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class LoggerFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.err.println("Request Remote Address: " + request.getRemoteAddr());
		chain.doFilter(request, response);
	}
}
/*
 * Spring security filter order
 * 1.- ChannelProcessingFilter, because it might need to redirect to a different protocol
 * 2.- SecurityContextPersistenceFilter, so a SecurityContext can be set up in the SecurityContextHolder at the beginning of a 
 *     web request, and any changes to the SecurityContext can be copied to the HttpSession when the web request ends (ready for
 *     use with the next web request)
 * 3.- ConcurrentSessionFilter, because it uses the SecurityContextHolder functionality but needs to update the SessionRegistry
 *     to reflect ongoing requests from the principal
 * 4.- Authentication processing mechanisms - UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, 
 *     BasicAuthenticationFilter etc - so that the SecurityContextHolder can be modified to contain a valid Authentication
 *     request token
 * 5.- The SecurityContextHolderAwareRequestFilter, if you are using it to install a Spring Security aware 
 *     HttpServletRequestWrapper into your servlet container
 * 6.- RememberMeAuthenticationFilter, so that if no earlier authentication processing mechanism updated the 
 *     SecurityContextHolder, and the request presents a cookie that enables remember-me services to take place, a suitable 
 *     remembered Authentication object will be put there
 * 7.- AnonymousAuthenticationFilter, so that if no earlier authentication processing mechanism updated the 
 *     SecurityContextHolder, an anonymous Authentication object will be put there
 * 8.- ExceptionTranslationFilter, to catch any Spring Security exceptions so that either an HTTP error response can be returned
 *     or an appropriate AuthenticationEntryPoint can be launched
 * 9.- FilterSecurityInterceptor, to protect web URIs and raise exceptions when access is denied
 */

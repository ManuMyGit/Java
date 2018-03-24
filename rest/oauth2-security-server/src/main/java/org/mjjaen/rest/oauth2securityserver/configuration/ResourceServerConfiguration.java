package org.mjjaen.rest.oauth2securityserver.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Autowired
    private ResourceServerTokenServices tokenServices;
	
	@Autowired
    private TokenStore tokenStore;
	
    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices).tokenStore(tokenStore);
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			//.anonymous().disable() Not enabled because anybody can access to public resource
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/public")
					.permitAll()
			//.and()
			//.requestMatcher(new Oauth2RequestedMatcher())
			//.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/private")
					.hasAuthority("ADMIN_USER")//.authenticated()
				.antMatchers(HttpMethod.GET, "/cities/**")
					.hasAnyAuthority("ADMIN_USER", "STANDARD_USER")//.authenticated()
				.antMatchers(HttpMethod.GET, "/users/**")
					.hasAnyAuthority("ADMIN_USER", "STANDARD_USER")
			/*.and()
			.authorizeRequests()
			.antMatchers("/private").access("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
			.antMatchers("/cities/**").access("#oauth2.hasScope('read')")*/
			.and()
			.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	//This class is used to add some extra functionality to the requestMatcher, that will be invoked if condition is true
	//Be careful to use it because if the condition doesn't match, the security will be not invoked and the resource will become insecure
	@SuppressWarnings("unused")
	private static class Oauth2RequestedMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			boolean haveToken = (auth != null) && auth.startsWith("Bearer");
			return haveToken;
		}
	}
}

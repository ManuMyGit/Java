package org.mjjaen.rest.oauth2securityserver;

import javax.servlet.ServletContextListener;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.CustomUserDetails;
import org.mjjaen.rest.oauth2securityserver.businessObject.repository.UserRepository;
import org.mjjaen.rest.oauth2securityserver.filters.WebFilter;
import org.mjjaen.rest.oauth2securityserver.listeners.MyServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class Oauth2SecurityServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(Oauth2SecurityServerApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception {
		builder.userDetailsService(username -> new CustomUserDetails(userRepository.findByUserName(username)));
	}
	
	@Bean
	public FilterRegistrationBean<WebFilter> filterRegistrationBean() {
		FilterRegistrationBean<WebFilter> registration = new FilterRegistrationBean<WebFilter>();
		registration.setFilter(new WebFilter());
		registration.addUrlPatterns("/cities/*");
		return registration;
	}
	
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> listenerRegistrationBean() {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new MyServletContextListener());
		return bean;
	}
}

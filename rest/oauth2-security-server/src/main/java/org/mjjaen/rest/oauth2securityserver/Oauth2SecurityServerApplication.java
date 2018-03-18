package org.mjjaen.rest.oauth2securityserver;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.CustomUserDetails;
import org.mjjaen.rest.oauth2securityserver.businessObject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class Oauth2SecurityServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(Oauth2SecurityServerApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception {
		builder.userDetailsService(username -> new CustomUserDetails(userRepository.findByUsername(username)));
	}
}

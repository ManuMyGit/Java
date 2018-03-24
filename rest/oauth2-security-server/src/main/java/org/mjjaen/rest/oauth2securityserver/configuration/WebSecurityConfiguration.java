package org.mjjaen.rest.oauth2securityserver.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier(value="userService")
    private UserDetailsService userDetailsService;

    @Value("${security.security-realm}")
    private String securityRealm;
    
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
       return super.authenticationManager();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(6);
	}
    
    @Bean
    public DaoAuthenticationProvider autenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(passwordEncoder());
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    /*
     * SessionCreationPolicy.ALWAYS: Always create an HttpSession
     * SessionCreationPolicy.IF_REQUIRED: Spring Security will only create an HttpSession if required
     * SessionCreationPolicy.NEVER: Spring Security will never create an HttpSession, but will use the HttpSession if it already exists
     * SessionCreationPolicy.STATELESS: Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
       	//.anonymous().disable()
       	.sessionManagement()
       	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       	.and()
       	.httpBasic().and()
       	//.httpBasic().realmName(securityRealm) Enable HTTP Base authentication
       	//.and()
       	.cors() //Cross Origin Resource Sharing
       	.and()
       	.csrf().disable(); //Cross Site Request Forgery

    }
    
	@Bean
    public CorsFilter corsFilter() {
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("content-length"));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

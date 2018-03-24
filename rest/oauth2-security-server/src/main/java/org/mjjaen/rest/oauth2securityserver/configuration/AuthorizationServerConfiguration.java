package org.mjjaen.rest.oauth2securityserver.configuration;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	//To use in case you want to configure the server in memory
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;
	
	@Value("${security.jwt.grant-type-authrozation}")
	private String authrozation;
	
	@Value("${security.jwt.grant-type-implicit}")
	private String implicit;
	
	@Value("${security.jwt.grant-type-password}")
	private String password;
	
	@Value("${security.jwt.grant-type-client}")
	private String client;
	
	@Value("${security.jwt.grant-type-refresh}")
	private String refresh;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private TokenEnhancer tokenEnhancer;
	   
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter));
		endpoints.tokenStore(tokenStore)
			.tokenEnhancer(enhancerChain)
			.authenticationManager(authenticationManager)
			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/*
		 * To check all options:
		 * https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
		 */
		security.checkTokenAccess("isAuthenticated()"); // Return true is the user is not anonymous
	}

	// Configurador que define los detalles del servicio cliente. Los detalles del
	// cliente pueden ser inicializados, o sólo referir a un almacén existente
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * In-memory or JDBC client. Important client attributes: clientId: (required)
		 * the client id. secret: (required for trusted clients) the client secret, if
		 * any. scope: The scope to which the client is limited. If scope is undefined
		 * or empty (the default) the client is not limited by scope.
		 * authorizedGrantTypes: Grant types that are authorized for the client to use.
		 * Default value is empty. authorities: Authorities that are granted to the
		 * client (regular Spring Security authorities).
		 */
		clients.jdbc(dataSource);
		//Example configuration directly in memory
		/*inMemory()
			.withClient(clientId)
			.secret(clientSecret)
			.authorizedGrantTypes(client, password, refresh)
			.scopes(scopeRead, scopeWrite)
			.resourceIds(resourceIds)
			.accessTokenValiditySeconds(60 * 10)
			.refreshTokenValiditySeconds(60 * 100);*/
	}
}

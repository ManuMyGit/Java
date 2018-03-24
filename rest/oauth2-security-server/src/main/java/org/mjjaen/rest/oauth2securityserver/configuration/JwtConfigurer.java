package org.mjjaen.rest.oauth2securityserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class JwtConfigurer {
	@Value("${security.signing-key}")
    private String signingKey;
	
	@Value("${security.use-simetric-key}")
	private boolean useSimetricKey;
	
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       if(useSimetricKey)
    	   converter.setSigningKey(signingKey);
       else {
    	   KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "MaYzkSjmkzPC57L".toCharArray()); //password store = MaYzkSjmkzPC57L 
           converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mykeyoauth2")); //alias = mykeyoauth2
       }
       return converter;
    }
	//If you'll resource server will be different, you don't have the certificate, just public key
	/*public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		Resource resource = new ClassPathResource("oauth2.jks");
		String publicKey = null;
		try {
			publicKey = IOUtils.toString(resource.getInputStream(),  Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		converter.setVerifierKey(publicKey);
		return null;
	}*/

    @Bean
    public TokenStore tokenStore() {
       return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    @Primary //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
       DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
       defaultTokenServices.setTokenStore(tokenStore());
       defaultTokenServices.setSupportRefreshToken(true);
       return defaultTokenServices;
    }
    
    /*
	 * In case the Resource Server is different from Authorization Server, the token services must be defined as following:
	 */
	/*
	@Primary
    @Bean
    public RemoteTokenServices tokenServices() {
		final RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl("http://localhost:8081/spring-security-oauth-server/oauth/check_token");
		tokenService.setClientId("fooClientIdPassword");
		tokenService.setClientSecret("secret");
		return tokenService;
	}
	*/
}

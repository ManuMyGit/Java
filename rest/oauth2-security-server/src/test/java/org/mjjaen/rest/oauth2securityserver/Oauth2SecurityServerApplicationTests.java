package org.mjjaen.rest.oauth2securityserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.oauth2securityserver.businessObject.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Oauth2SecurityServerApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static AccessToken accessTokenAdmin;
	private static AccessToken accessTokenUser;

	@Test
	public void test01NonSecureResource() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/public", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		String hello = responseEntity.getBody();
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(hello != null);
		assertTrue(hello.equals("Hello public"));
	}
	
	@Test
	public void test02SecureResourcePrivateWithNoAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/private", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void test03SecureResourceCityWithNoAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<City> responseEntity = restTemplate.exchange("/cities", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<City>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
		responseEntity = restTemplate.exchange("/cities/1", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<City>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void test04GetTokenWithGoodAuthentication() {
		//Admin
		RequestEntity<MultiValueMap<String, String>> req = getRequestAndHeader("clientoauth2jwt", "XY7kmzoNzl100", "User1", "jwtpass", "password", null);
		ResponseEntity<AccessToken> response = restTemplate.exchange(req, AccessToken.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		AccessToken accessToken = response.getBody();
		assertNotNull(accessToken);
		accessTokenAdmin = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
		//User
		req = getRequestAndHeader("clientoauth2jwt", "XY7kmzoNzl100", "User2", "jwtpass", "password", null);
		response = restTemplate.exchange(req, AccessToken.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(accessToken);
		accessToken = response.getBody();
		accessTokenUser = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
	}
	
	@Test
	public void test05GetTokenWithWrongAuthentication() {
		RequestEntity<MultiValueMap<String, String>> req = getRequestAndHeader("otherclient", "XY7kmzoNzl100", "User1", "jwtpass", "password", null);
		ResponseEntity<AccessToken> response = restTemplate.exchange(req, AccessToken.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	public void test06SecureResourceCityWithAdminAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenAdmin.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<City>> responseEntity = restTemplate.exchange("/cities", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<City>>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<City> responseEntityId = restTemplate.exchange("/cities/1", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<City>() {});
		assertTrue(responseEntityId.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void test07SecureResourceCityWithUserAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenUser.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<City>> responseEntity = restTemplate.exchange("/cities", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<City>>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<City> responseEntityId = restTemplate.exchange("/cities/1", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<City>() {});
		assertTrue(responseEntityId.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void test09SecureResourcePrivateWithAdminAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenAdmin.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/private", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void test10SecureResourcePrivateWithUserAuthentication() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenUser.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/private", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.FORBIDDEN));
	}
	
	@Test
	public void test11SecureResourcePrivateWithAdminAuthenticationTokenOutOfDate() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenAdmin.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/private", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void test12SecureResourcePrivateWithAdminAuthenticationRefreshToken() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		
		RequestEntity<MultiValueMap<String, String>> req = getRequestAndHeader("clientoauth2jwt", "XY7kmzoNzl100", null, null, "refresh_token", accessTokenAdmin.getRefresh_token());
		ResponseEntity<AccessToken> response = restTemplate.exchange(req, AccessToken.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		AccessToken accessToken = response.getBody();
		assertNotNull(accessToken);
		accessTokenAdmin = accessToken;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessTokenAdmin.getAccess_token());
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/private", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {});
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
	}
	
	private RequestEntity<MultiValueMap<String, String>> getRequestAndHeader(String client, String passwordClient, String user, String password, String grant_type, String refreshToken) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/oauth/token")
				.queryParam(user != null ? "username" : "", user != null ? user : "")
				.queryParam(password != null ? "password" : "refresh_token", password != null ? password : refreshToken)
				.queryParam("grant_type", grant_type);
			
		HttpHeaders headers = new HttpHeaders();
		String plainCreds = client + ":" + passwordClient;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		headers.add("Authorization", "Basic "+ base64Creds);
		
		RequestEntity<MultiValueMap<String, String>> req = new RequestEntity<MultiValueMap<String, String>>(null, headers, HttpMethod.POST, builder.buildAndExpand().toUri());
		return req;
	}

	public TestRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}

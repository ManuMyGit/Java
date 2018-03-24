package org.mjjaen.rest.oauth2securityserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.oauth2securityserver.businessObject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Oauth2SecurityServerApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomPort;
	
	private String host;
	private String uriAccessToken;
	private Header headerContentType;
	
	private static final String CLIENT_PASSWORD_GRANT = "clientoauth2jwtpassword";
	private static final String PASSWORD_CLIENT_PASSWORD_GRANT = "XY7kmzoNzl100";
	private static final String WRONG_CLIENT_PASSWORD_GRANT = "otherclient";
	
	private static final String CLIENT_CLIENT_CREDENTUALS_GRANT = "clientoauth2jwtclient";
	private static final String PASSWORD_CLIENT_CLIENT_CREDENTIALS_GRANT = "XY7kmzoNzl100";
	private static final String WRONG_CLIENT_CLIENT_CREDENTIALS_GRANT = "otherclient";
	
	private static final String USER_ADMIN = "User1";
	private static final String PASSWORD_USER_ADMIN = "jwtpass";
	private static final String USER_STANDARD = "User2";
	private static final String PASSWORD_USER_STANDARD = "jwtpass";
	
	private static final String GRANT_PASSWORD = "password";
	private static final String GRANT_CLIENT_CREDENTIALS = "client_credentials";
	private static final String GRANT_REFRESH_TOKEN = "refresh_token";
	
	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_GRANT_TYPE = "grant_type";
	private static final String PARAM_REFRESH_TOKEN = "refresh_token";
	
	private static AccessToken accessTokenAdmin;
	private static AccessToken accessTokenUser;
	private static AccessToken accessTokenClient;
	
	@Before
	public void config() {
		this.host = "http://localhost:".concat(new Integer(randomPort).toString());
		this.uriAccessToken = this.host.concat("/oauth/token");
		this.headerContentType = new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
	
	/*
	 * Password grant tests
	 */
	@Test
	public void test01NonSecureResource() {
		Response response = RestAssured.given().header(headerContentType).get(this.host.concat("/public"));
		String hello = response.asString();
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		assertTrue(hello != null);
		assertTrue(hello.equals("Hello public"));
	}
	
	@Test
	public void test02SecureResourcePrivateWithNoAuthentication() {
		Response response = RestAssured.given().header(headerContentType).get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void test03SecureResourceCityWithNoAuthentication() {
		Response response = RestAssured.given().header(headerContentType).get(this.host.concat("/cities"));
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED.value());
		response = RestAssured.given().header(headerContentType).get(this.host.concat("/cities/1"));
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void test04GetTokenWithGoodAuthentication() {
		//Admin
		Response response = obtainAccessToken(CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, USER_ADMIN, PASSWORD_USER_ADMIN, GRANT_PASSWORD, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		AccessToken accessToken = response.as(AccessToken.class);
		assertNotNull(accessToken);
		accessTokenAdmin = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
		//User
		response = obtainAccessToken(CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, USER_STANDARD, PASSWORD_USER_STANDARD, GRANT_PASSWORD, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		accessToken = response.as(AccessToken.class);
		assertNotNull(accessToken);
		accessTokenUser = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
	}
	
	@Test
	public void test05GetTokenWithWrongAuthentication() {
		Response response = obtainAccessToken(WRONG_CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, USER_STANDARD, PASSWORD_USER_ADMIN, GRANT_PASSWORD, null);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@Test
	public void test06SecureResourceCityWithAdminAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/cities"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/cities/1"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	@Test
	public void test07SecureResourceCityWithUserAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenUser)))
				.header(headerContentType)
				.get(this.host.concat("/cities"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenUser)))
				.header(headerContentType)
				.get(this.host.concat("/cities/1"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	@Test
	public void test08SecureResourcePrivateWithAdminAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	@Test
	public void test09SecureResourcePrivateWithUserAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenUser)))
				.header(headerContentType)
				.get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN.value());
	}
	
	@Test
	public void test10SecureResourcePrivateWithAdminAuthenticationTokenOutOfDate() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void test11SecureResourcePrivateWithAdminAuthenticationRefreshToken() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		
		Response response = obtainAccessToken(CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, null, null, GRANT_REFRESH_TOKEN, accessTokenAdmin);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		AccessToken accessToken = response.as(AccessToken.class);
		assertNotNull(accessToken);
		accessTokenAdmin = accessToken;
		
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	/*
	 * Client_credentials grant tests
	 */
	@Test
	public void test12GetTokenWithWrongAuthentication() {
		Response response = obtainAccessToken(WRONG_CLIENT_CLIENT_CREDENTIALS_GRANT, PASSWORD_CLIENT_CLIENT_CREDENTIALS_GRANT, null, null, GRANT_CLIENT_CREDENTIALS, null);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@Test
	public void test13GetTokenWithGoodAuthentication() {
		Response response = obtainAccessToken(CLIENT_CLIENT_CREDENTUALS_GRANT, PASSWORD_CLIENT_CLIENT_CREDENTIALS_GRANT, null, null, GRANT_CLIENT_CREDENTIALS, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.as(AccessToken.class).getRefresh_token() == null);
		accessTokenClient = response.as(AccessToken.class);
	}
	
	@Test
	public void test14SecureResourceCityWithClientAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenClient)))
				.header(headerContentType)
				.get(this.host.concat("/cities"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenClient)))
				.header(headerContentType)
				.get(this.host.concat("/cities/1"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	@Test
	public void test15SecureResourcePrivateWithClientNoAdminAuthentication() {
		Response response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenClient)))
				.header(headerContentType)
				.get(this.host.concat("/private"));
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN.value());
	}
	
	/*
	 * Test user
	 */
	@Test
	public void test16SecureResourceAdminWithPasswordAuthentication() {
		Response response = obtainAccessToken(CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, USER_ADMIN, PASSWORD_USER_ADMIN, GRANT_PASSWORD, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		AccessToken accessToken = response.as(AccessToken.class);
		assertNotNull(accessToken);
		accessTokenAdmin = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenAdmin)))
				.header(headerContentType)
				.get(this.host.concat("/users"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		assertTrue(!response.as(User.class).getUserName().isEmpty());
		assertTrue(response.as(User.class).getUserName().equals(USER_ADMIN));
	}
	
	@Test
	public void test17SecureResourceUserWithPasswordAuthentication() {
		Response response = obtainAccessToken(CLIENT_PASSWORD_GRANT, PASSWORD_CLIENT_PASSWORD_GRANT, USER_STANDARD, PASSWORD_USER_STANDARD, GRANT_PASSWORD, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		AccessToken accessToken = response.as(AccessToken.class);
		assertNotNull(accessToken);
		accessTokenUser = accessToken;
		assertNotNull(accessToken.getAccess_token());
		assertNotNull(accessToken.getRefresh_token());
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenUser)))
				.header(headerContentType)
				.get(this.host.concat("/users"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		assertTrue(!response.as(User.class).getUserName().isEmpty());
		assertTrue(response.as(User.class).getUserName().equals(USER_STANDARD));
	}
	
	@Test
	public void test18SecureResourceWithClientAuthentication() {
		Response response = obtainAccessToken(CLIENT_CLIENT_CREDENTUALS_GRANT, PASSWORD_CLIENT_CLIENT_CREDENTIALS_GRANT, null, null, GRANT_CLIENT_CREDENTIALS, null);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.as(AccessToken.class).getRefresh_token() == null);
		accessTokenClient = response.as(AccessToken.class);
		response = RestAssured.given()
				.header(new Header("Authorization", getBearerStringToken(accessTokenClient)))
				.header(headerContentType)
				.get(this.host.concat("/users"));
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
		assertTrue(response.body().asString().isEmpty());
	}
	
	@Test
	public void test19SecureResourceUserWithNoAuthentication() {
		Response response = RestAssured.given().header(headerContentType).get(this.host.concat("/users"));
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED.value());
	}
	
	private Response obtainAccessToken(String clientId, String clientPassword, String userName, String password, String grantType, AccessToken accessToken) {
		Map<String, String> params = new HashMap<String, String>();
		if(userName != null)
			params.put(PARAM_USERNAME, userName);
		if(password != null)
			params.put(PARAM_PASSWORD, password);
		if(grantType != null)
			params.put(PARAM_GRANT_TYPE, grantType);
		if(accessToken != null)
			params.put(PARAM_REFRESH_TOKEN, accessToken.getRefresh_token());
		Response response = RestAssured.given()
				.auth().preemptive().basic(clientId, clientPassword)
				.and().with().params(params).when()
				.post(this.uriAccessToken);
		return response;
	}
	
	private String getBearerStringToken(AccessToken accessToken) {
		return "Bearer ".concat(accessToken.getAccess_token());
	}

	public TestRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}

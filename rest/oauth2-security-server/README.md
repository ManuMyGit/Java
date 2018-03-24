# OAUTH2

# About the project
- This project uses Oauth2 to secure an simple API. The main characteristics are:
    - There are public and private resources.
    - There are two type of private resources. Those which can be retrieved by standar users and others than can be retrieved just by admin users.
    - Use JWT to enhance token with some information more.
    - Cors filter.
    - Clients and user are saved in a embebbed database (H2 database).
    - In the code you can check how to use inMemory clients.
    - Resources are protected by authorities (roles), but in the code you can check also how to use scopes (read, write, ...).
    - A complete set of integration tests to test public and private tests, taking into account the authorities to access those resources as well as to test refresh token.
    - Client-credentials and password,refresh_token grants.
    - URIs
    	- /public (GET, public access)
    	- /private (GET, just ADMIN users)
    	- /cities and /cities/{id} (GET, ADMIN and STANDARD users)
	- /users (GET, ADMIN and STANDARD users)
    	- /oauth/token (to get access token)
	- Client password and refresh_token grant: clientoauth2jwtpassword/XY7kmzoNzl100
	- Client client_credentials grant: clientoauth2jwtclient/XY7kmzoNzl100
	- User Admin: User1/jwtpass
	- User Standard: User2/jwtpass

# Definition
- OAuth 2 is an authorization framework that enables applications to obtain limited access to user accounts without using directly user credentials. It works by delegating user authentication to the service that hosts the user account, and authorizing third-party applications to access the user account. Oauth2 is based on roles and grant types.

# Oauth2 Roles
- Resource owner (User): owner of the resource. It is an entity which can offer access to secure resources.
- Client (Application): it is an application which wants to access to secure resources with the authorization of the resource owner.
- Provider (API):
    - Resource server: it is the entity which contains secure resources. It can accept and reply requests by using an access token that is mandatory in the request.
    - Authorization server: it is in charge of generating access tokens after validating user credentials. In some cases, resource server and authorization server are the same.

# Abstract Protocol Flow
1. Client apply for authorization to the resource owner. El cliente solicita autorización al propietario del recurso. The request can be done directly to the authorization server instead of the resource owner.
2. Resource owner grants (or deny) the authorization to access the secure resource by informing the client with one of the four grant types (authorization code, implicit, resource owner password credentials or client credentials). This kind of concession depends on the concession type requested by the client at the beginnig of the flow.
3. Client apply for an access token to the authorization server. It must identify itself and present the authorization of the previous step.
4. Authorization server validate client credentials and the autorhization. If they are valid, an access token will be returned.
5. Cliend access to resource server with the token.
6. Resource server returns the secure resource.

# OAuth2 Grant Types
1. Authorization Code (authorization_code)
    - This kind of grant is used when web applications try to access to the resource server (server to server). The flow is the following:
        1. The client initiates the flow by directing the resource owner’s user-agent to the authorization endpoint. The client includes its client identifier, requested scope, local state, and a redirection URI to which the authorization server will send the user-agent back once access is granted (or denied).
        2. The authorization server authenticates the resource owner (via the user-agent) and establishes whether the resource owner grants or denies the client’s access request.
        3. Assuming the resource owner grants access, the authorization server redirects the user-agent back to the client using the redirection URI provided earlier (in the request or during client registration). The redirection URI includes an authorization code and any local state provided by the client earlier.
        4. The client requests an access token from the authorization server’s token endpoint by including the authorization code received in the previous step. When making the request, the client authenticates with the authorization server. The client includes the redirection URI used to obtain the authorization code for verification.
        5. The authorization server authenticates the client, validates the authorization code, and ensures that the redirection URI received matches the URI used to redirect the client in step (3). If valid, the authorization server responds back with an access token and, optionally, a refresh token.
2. Implicit (implicit)
    - The implicit grant is similar to the authorization code grant with two distinct differences. It is intended to be used for user-agent-based clients (e.g. single page web apps) that can’t keep a client secret because all of the application code and storage is easily accessible. Secondly instead of the authorization server returning an authorization code which is exchanged for an access token, the authorization server returns an access token.
        1. The client initiates the flow by directing the resource owner’s user-agent to the authorization endpoint. The client includes its client identifier, requested scope, local state, and a redirection URI to which the authorization server will send the user-agent back once access is granted (or denied).
        2. The authorization server authenticates the resource owner (via the user-agent) and establishes whether the resource owner grants or denies the client’s access request.
        3. Assuming the resource owner grants access, the authorization server redirects the user-agent back to the client using the redirection URI provided earlier. The redirection URI includes the access token in the URI fragment.
        4. The user-agent follows the redirection instructions by making a request to the web-hosted client resource. The user-agent retains the fragment information locally.
        5. The web-hosted client resource returns a web page (typically an HTML document with an embedded script) capable of accessing the full redirection URI including the fragment retained by the user-agent, and extracting the access token (and other parameters) contained in the fragment.
        6. The user-agent executes the script provided by the web-hosted client resource locally, which extracts the access token.
        7. The user-agent passes the access token to the client.
3. Resource Owner Password Credentials (password)
    - This type of aurhorization is used when client is totally trustworthy and the user trusts so much that he gives its username and password to the client to access the resource. These credentials can't be stored in the client, it is only used to get the access token.
        1. The resource owner provides the client with its username and password.
        2. The client requests an access token from the authorization server’s token endpoint by including the credentials received from the resource owner. When making the request, the client authenticates with the authorization server.
        3. The authorization server authenticates the client and validates the resource owner credentials, and if valid, issues an access token.
4. Client Credentials (client-credentials)
    - The client can request an access token using only its client credentials (or other supported means of authentication) when the client is requesting access to the protected resources **under its control**, or those of another resource owner that have been previously arranged with the authorization server.
        1. The client authenticates with the authorization server and requests an access token from the token endpoint.
        2. The authorization server authenticates the client, and if valid, issues an access token.

# What kind of grant to use?
To dedice this, you can use the following diagram:
- Who is the owner of the access token?
	- A machine -> **Use client credential grant**
	- An user:
		- What's the type of type client?
			- A web application -> Use **authorization code grant**
			- A native application:
				- First party or third party client?
					- First party -> **Use password grant**
					- Third party -> **Use authorization code grant**
			- An user-agent-based application:
				- First party or third party client?
					- First party -> **Use password grant**
					- Third party -> **Use implicit grant**
# Refresh token
- Access tokens must have a expiration period after which they are considered expired and they must be refused by the server, forcing the client to get a new token. In order to avoid requesting again and to repeat all the validation process to get a new token, a refresh token is used. When an access token is granted, a refresh token can be included asociated to the access token. With this refresh token, a new access token can be requested:
    1. The client requests an access token.
    2. The authorization server validates the request and return an access token.
    3. The clien requests a secure resource to the server with the access token.
    4. The rerousce server validates the token and it returns the secure resource if the token is still valid.
    5. Setps 3 and 4 are repeated until the resource server detects the access token has expired. When the client get a response with invalid token (expired token) gos to step 7.
    6. When the access token is invalid, the server informs to the client.
    7. The client use the refresh token to get a new access token (just with the refresh token).
    8. The authentication server validates to the client and the refresh token. If so, it returns a new access token.

# Retrieve User and Authentication
- There are several ways to retrieve user and authentication object in controller
	- User
		1. As a method argument in controller method: Principal user
		2. From Authentication object (see how to retrieve Authentication object): auth.getPrincipal()
		3. From the HttpServletRequest object (which can be retrieve as a method argument as HttpServletRequest request): request.getUserPrincipal()
	- Authentication
		1. As a method argument in controller method: Authentication auth
		2. From static context: SecurityContextHolder.getContext().getAuthentication()
		- Properties of Authentication
			- Credentials
			- Details
				- Cast to OAuth2AuthenticationDetails to access the following properties:
					- DecodedDetails (Object)
					- RemoteAddress (String)
					- SessionId (String)
					- TokenType (String)
					- TokenValue (String)
			- Name
			- Principal
			- Authorities (Collection<? extends GrantedAuthority>)
	- OAuth2Authentication
		1. As a method argument in controller method: OAuth2Authentication auth
		2. From static context: (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication() (cast needed)
		- Properties of OAuth2Authentication:
			- OAuth2Authentication extends AbstractAuthenticationToken which implements Authentication
			- UserAuthentication (Authentication)
			- OAuth2Request (OAuth2Request)
				- ClientId (String)
				- GrantType (String)
				- RedirectUrl (String)
				- Authorities (Collection<? extends GrantedAuthority>)
				- Extensions (Map<String, Serializable>)
				- RefreshTokenRequest (TokenRequest)
					- ClientId (String)
					- GrantType (String)
					- RequestParameters (Map<String, String>)
					- Scope (Set<String>)
				- RequestParameters (Map<String, String>)
				- ResourceIds (Set<String>)
				- ResponseType (Set<String>)
				- Scope (Set<String>)

# How to access token information in Resource Server
- In the previous point, you could see how to retrieve OAuth2AuthenticationDetails (details). Once you do that, you can access token information through TokenStore:
	- OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
	- These are the main properties:
		- AdditionalInformation (Map<String, Object>)
		- Expiration (Date)
		- ExpiresIn (int)
		- RefreshToken (OAuth2RefreshToken)
			- Value (String)
		- Scope (Set<String>)
		- TokenType (String)
		- Value (String)

# Filters
- There are several ways to add a Filter:
	1. Create a new Bean which returns a Filter. This'll apply to all requests. The Bean must be declared inside @Configuration context.
	2. Create a class which implements Filter. If this class is anotated with @Component, the filter'll apply to all requests. It is mandatory to declare an order with @Order (@Order(Ordered.HIGHEST_PRECEDENCE), @Order(Ordered.LOWEST_PRECEDENCE), @Order(1), ...).
	3. Create a class which implements Filter but outside @Configuration context. To apply this filter, you need to declare a @Bean inside @Configuration context which returns a FilterRegistrationBean object. In this object, you can choose the URL pattefdsrns where filt
	4. Create a class which extends GenericFilterBean. You can use this filter in Spring Security in the method configure(HttpSecurity http) with addFilterBefore(filter, class), addFilterAfter(filter, class), addFilterAt(filter, class) and addFilter(filter) (in this case it is mandatory to declare an order in filter chain). It'll apply to all requests. Spring security filter order in chain filter is the followint:
		1. ChannelProcessingFilter, because it might need to redirect to a different protocol.
		2. SecurityContextPersistenceFilter, so a SecurityContext can be set up in the SecurityContextHolder at the beginning of a web request, and any changes to the SecurityContext can be copied to the HttpSession when the web request ends (ready for use with the next web request).
		3. ConcurrentSessionFilter, because it uses the SecurityContextHolder functionality but needs to update the SessionRegistry to reflect ongoing requests from the principal.
		4. Authentication processing mechanisms - UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, BasicAuthenticationFilter etc - so that the SecurityContextHolder can be modified to contain a valid Authentication request token.
		5. The SecurityContextHolderAwareRequestFilter, if you are using it to install a Spring Security aware HttpServletRequestWrapper into your servlet container.
		6. RememberMeAuthenticationFilter, so that if no earlier authentication processing mechanism updated the SecurityContextHolder, and the request presents a cookie that enables remember-me services to take place, a suitable remembered Authentication object will be put there.
		7. AnonymousAuthenticationFilter, so that if no earlier authentication processing mechanism updated the SecurityContextHolder, an anonymous Authentication object will be put there.
		8. ExceptionTranslationFilter, to catch any Spring Security exceptions so that either an HTTP error response can be returned or an appropriate AuthenticationEntryPoint can be launched.
		9. FilterSecurityInterceptor, to protect web URIs and raise exceptions when access is denied. 

# Listeners
- It is easy to declare a listener. Just to declare a class which implements ServletContextListener and to declare a @Bean inside @Configuration context which returns a ServletListenerRegistrationBean. To access the Servlet context just to use getServletContext() method.

# Integration tests included
PASSWORD,REFRESH TOKEN GRANTS
1. Access /public resource with no user -> OK (200).
2. Access /private resource with no user -> UNAUTHORIZED (401).
3. Access /cities and /cities/1 with no user -> UNAUTHORIZED (401).
4. Get token to admin and standard users with correct authentication -> OK (200).
5. Get token with wrong authentication -> UNAUTHORIZED (401).
6. Access /cities and /cities/1 with admin user token -> OK (200).
7. Access /cities and /cities/1 with standard user token -> OK (200).
8. Access /private with admin user token -> OK (200).
9. Access /private with standard user token -> FORBIDDEN (403).
10. Access /private with admin user token out of date -> UNAUTHORIZED (401).
11. Access /private with admin user refresh token -> OK (200).
12. Access /cities with admin user token with no changes to the resource -> First access 200 (OK). Second access (with If-None-Match in header) 304 (NOT_MODIFIED).

CLIENT_CREDENTIAL GRANT WITH NO ADMIN CLIENT
1. Get token with wrong authentication -> UNAUTHORIZED (401).
2. Get token with correcto authentication -> OK (200).
3. Access /cities and /cities/1 with client token -> OK (200).
4. Access /private with client no admin token -> FORBIDDEN (403).

USERS
1. Access /users with password grant and admin user -> OK (200).
2. Access /users with password grant and standard user -> OK (200).
3. Access /users with client_credentials grant -> OK (200).
4. Access /users with with no user -> UNAUTHORIZED (401).

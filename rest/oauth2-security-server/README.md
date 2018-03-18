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
    - Client-credentials and password grants.

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
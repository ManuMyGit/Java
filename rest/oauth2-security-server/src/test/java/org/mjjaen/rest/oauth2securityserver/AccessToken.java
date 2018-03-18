package org.mjjaen.rest.oauth2securityserver;

public class AccessToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private String organization;
	private String jti;
	
	public AccessToken() {
		super();
	}
	
	public AccessToken(String access_token, String token_type, String refresh_token, int expires_in, String scope,
			String organization, String jti) {
		super();
		this.access_token = access_token;
		this.token_type = token_type;
		this.refresh_token = refresh_token;
		this.expires_in = expires_in;
		this.scope = scope;
		this.organization = organization;
		this.jti = jti;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public String getToken_type() {
		return token_type;
	}
	
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	public int getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getJti() {
		return jti;
	}
	
	public void setJti(String jti) {
		this.jti = jti;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((access_token == null) ? 0 : access_token.hashCode());
		result = prime * result + expires_in;
		result = prime * result + ((jti == null) ? 0 : jti.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((refresh_token == null) ? 0 : refresh_token.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((token_type == null) ? 0 : token_type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		if (access_token == null) {
			if (other.access_token != null)
				return false;
		} else if (!access_token.equals(other.access_token))
			return false;
		if (expires_in != other.expires_in)
			return false;
		if (jti == null) {
			if (other.jti != null)
				return false;
		} else if (!jti.equals(other.jti))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (refresh_token == null) {
			if (other.refresh_token != null)
				return false;
		} else if (!refresh_token.equals(other.refresh_token))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (token_type == null) {
			if (other.token_type != null)
				return false;
		} else if (!token_type.equals(other.token_type))
			return false;
		return true;
	}
}

package org.mjjaen.rest.oauth2securityserver.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.City;
import org.mjjaen.rest.oauth2securityserver.businessObject.model.User;
import org.mjjaen.rest.oauth2securityserver.businessObject.service.CityService;
import org.mjjaen.rest.oauth2securityserver.businessObject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private TokenStore tokenStore;
	
	@GetMapping("/public")
	public String getHello() {
		return "Hello public";
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER')") Spring security
	//@CrossOrigin(origins = "http://localhost:9000") Concrete CORS configuration
	@GetMapping("/private")
	public String getPrivate(Principal user, Authentication auth, @RequestHeader(name = "remote_addr", required = false) String remoteAddress) {
		System.err.println("Header added in filter: " + remoteAddress);
		return "Hello private";
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')") Spring security
	@SuppressWarnings("unused")
	@GetMapping("/cities")
	public List<City> getCities(OAuth2Authentication auth, @RequestHeader(name = "remote_addr", required = false) String remoteAddress) {
		//Retrieve access token and its information
		System.err.println("Header added in filter: " + remoteAddress);
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
		List<City> lista = new ArrayList<City>();
		cityService.findAll().forEach(lista::add);
		return lista;
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')") Spring security
	@GetMapping("/cities/{id}")
	public City getCity(@PathVariable(name="id", required=true) Integer id, HttpServletRequest request) {
		return cityService.findById(id).get();
	}
	
	@GetMapping("/users")
	public User getUser(Principal principal) {
		User user = userService.findByUserName(principal.getName());
		return user;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
}

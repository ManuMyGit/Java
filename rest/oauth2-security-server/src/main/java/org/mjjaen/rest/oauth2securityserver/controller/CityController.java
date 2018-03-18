package org.mjjaen.rest.oauth2securityserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.City;
import org.mjjaen.rest.oauth2securityserver.businessObject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
	@Autowired
	private CityService cityService;
	
	@GetMapping("/public")
	public String getHello() {
		return "Hello public";
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER')")
	//@CrossOrigin(origins = "http://localhost:9000") Concrete CORS configuration
	@GetMapping("/private")
	public String getPrivate() {
		return "Hello private";
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping("/cities")
	public List<City> getCities() {
		List<City> lista = new ArrayList<City>();
		cityService.findAll().forEach(lista::add);
		return lista;
	}
	
	//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping("/cities/{id}")
	public City getCity(@PathVariable(name="id", required=true) Integer id) {
		return cityService.findById(id).get();
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
}

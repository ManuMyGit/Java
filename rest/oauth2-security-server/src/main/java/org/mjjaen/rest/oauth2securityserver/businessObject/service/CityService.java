package org.mjjaen.rest.oauth2securityserver.businessObject.service;

import java.util.Optional;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.City;

public interface CityService {
	public Iterable<City> findAll();
	public Optional<City> findById(Integer id);
}

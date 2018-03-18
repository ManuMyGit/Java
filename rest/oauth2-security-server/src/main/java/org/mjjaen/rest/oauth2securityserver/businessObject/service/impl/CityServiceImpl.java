package org.mjjaen.rest.oauth2securityserver.businessObject.service.impl;

import java.util.Optional;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.City;
import org.mjjaen.rest.oauth2securityserver.businessObject.repository.CityRepository;
import org.mjjaen.rest.oauth2securityserver.businessObject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public Iterable<City> findAll() {
		return cityRepository.findAll();
	}

	@Override
	public Optional<City> findById(Integer id) {
		return cityRepository.findById(id);
	}

	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

}

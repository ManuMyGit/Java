package org.mjjaen.microservices.currencyexchangeservice.service;

import org.mjjaen.microservices.currencyexchangeservice.bean.ExchangeValue;
import org.mjjaen.microservices.currencyexchangeservice.repository.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeValueService {
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	public ExchangeValue findOneByFromAndTo(String from, String to) {
		return exchangeValueRepository.findByFromAndTo(from, to);
	}
	
	public ExchangeValueRepository getExchangeValueRepository() {
		return exchangeValueRepository;
	}

	public void setExchangeValueRepository(ExchangeValueRepository exchangeValueRepository) {
		this.exchangeValueRepository = exchangeValueRepository;
	}
}

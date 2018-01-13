package org.mjjaen.microservices.currencyexchangeservice.controller;

import org.mjjaen.microservices.currencyexchangeservice.bean.ExchangeValue;
import org.mjjaen.microservices.currencyexchangeservice.service.ExchangeValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/microservices")
public class Controller {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueService exchangeValueService;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = exchangeValueService.findOneByFromAndTo(from, to);
		if(exchangeValue == null)
			exchangeValue = new ExchangeValue(null, null, null, null);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("{}", exchangeValue);
		return exchangeValue;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public ExchangeValueService getExchangeValueService() {
		return exchangeValueService;
	}

	public void setExchangeValueService(ExchangeValueService exchangeValueService) {
		this.exchangeValueService = exchangeValueService;
	}
}

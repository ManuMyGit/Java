package org.mjjaen.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.mjjaen.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import org.mjjaen.microservices.currencyconversionservice.bean.LimitsConfiguration;
import org.mjjaen.microservices.currencyconversionservice.common.exceptions.LimitsExceededException;
import org.mjjaen.microservices.currencyconversionservice.feignclients.CurrencyExchangeServiceProxy;
import org.mjjaen.microservices.currencyconversionservice.feignclients.LimitsServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/microservices")
public class Controller {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	@Autowired
	private LimitsServiceProxy limitsServiceProxy;
	
	/*
	 * Unused method, just to compare a simple call with Feign to a more complicated call with RestTemplate
	 */
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/microservices/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(1L, response.getFrom(), response.getTo(), response.getConversionMultiple(), quantity, response.getPort());
		return currencyConversionBean;
	}
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveExchangeValueFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		LimitsConfiguration limitsConfiguration = limitsServiceProxy.retrieveLimits();
		if(new BigDecimal(limitsConfiguration.getMinimum()).compareTo(quantity) <= 0 && new BigDecimal(limitsConfiguration.getMaximum()).compareTo(quantity) >= 0) {
			CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
			logger.info("{}", response);
			return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, response.getPort());
		} else
			throw new LimitsExceededException(limitsConfiguration.toString());
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public CurrencyExchangeServiceProxy getCurrencyExchangeServiceProxy() {
		return currencyExchangeServiceProxy;
	}

	public void setCurrencyExchangeServiceProxy(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
		this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
	}

	public LimitsServiceProxy getLimitsServiceProxy() {
		return limitsServiceProxy;
	}

	public void setLimitsServiceProxy(LimitsServiceProxy limitsServiceProxy) {
		this.limitsServiceProxy = limitsServiceProxy;
	}
}

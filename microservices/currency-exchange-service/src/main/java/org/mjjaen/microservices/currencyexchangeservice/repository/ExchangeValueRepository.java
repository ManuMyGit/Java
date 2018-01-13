package org.mjjaen.microservices.currencyexchangeservice.repository;

import org.mjjaen.microservices.currencyexchangeservice.bean.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
	public ExchangeValue findByFromAndTo(String from, String to);
}

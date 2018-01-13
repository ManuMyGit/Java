package org.mjjaen.microservices.currencyconversionservice.feignclients;

import org.mjjaen.microservices.currencyconversionservice.bean.LimitsConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "limits-service")
public interface LimitsServiceProxy {
	@GetMapping("/limits-service/microservices/limits")
	public LimitsConfiguration retrieveLimits();
}

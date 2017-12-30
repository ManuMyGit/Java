package org.mjjaen.rest.internationalization.controller;

import java.util.Locale;

import org.mjjaen.rest.internationalization.model.businessObject.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path="/webservices")
public class InternationalizationController {
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world-internationalized/{name}")
	public HelloWorldBean helloWorldInternationalized(@PathVariable(required = false) String name, @RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return new HelloWorldBean(messageSource.getMessage("good.morning.message", new Object[]{name != null && !name.isEmpty() ? name : "unknown"}, locale));
	}
}

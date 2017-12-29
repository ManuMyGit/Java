package org.mjjaen.rest.helloworldservice.controller;

import org.mjjaen.rest.helloworldservice.model.businessObject.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices")
public class HelloWorldController {
	@GetMapping(path="/hello-world") //Esto es lo mismo que lo anterior, y te ahorras el method
	public HelloWorldBean helloWorld(@RequestParam(required=false, defaultValue="unknown") String name) {
		return new HelloWorldBean(name);
	}
}
package org.mjjaen.rest.secutiry.controller;

import org.mjjaen.rest.secutiry.model.businessObject.SecurityBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices")
public class SecurityController {
	@GetMapping(path="/security") //Esto es lo mismo que lo anterior, y te ahorras el method
	public SecurityBean security() {
		return new SecurityBean();
	}
}
package org.mjjaen.rest.versioningservice.controller;

import org.mjjaen.rest.versioningservice.model.businessObject.Name;
import org.mjjaen.rest.versioningservice.model.businessObject.PersonV1;
import org.mjjaen.rest.versioningservice.model.businessObject.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices")
public class PersonVersioningController {
	//URI example
	@GetMapping("/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Manuel");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Manuel", "Jaén"));
	}
	
	//Param URI example
	@GetMapping(value="/person/param", params="version=1")
	public PersonV1 paramV1() {
		return new PersonV1("Manuel");
	}
	
	@GetMapping(value="/person/param", params="version=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("Manuel", "Jaén"));
	}
	
	//Header example
	@GetMapping(value="/person/header", headers="X-API-VERSION=1")
	public PersonV1 headerV1() {
		return new PersonV1("Manuel");
	}
	
	@GetMapping(value="/person/header", headers="X-API-VERSION=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("Manuel", "Jaén"));
	}
	
	//Content-Type Header example 
	@GetMapping(value="/person/negotiation", produces="application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("Manuel");
	}
	
	@GetMapping(value="/person/negotiation", produces="application/vnd.company.app-v2+json")
	public PersonV2 produces() {
		return new PersonV2(new Name("Manuel", "Jaén"));
	}
}

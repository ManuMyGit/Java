package org.mjjaen.rest.contentnegotiation.controller;

import org.mjjaen.rest.contentnegotiation.model.businessObject.ContentBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices")
public class ContentController {
	@GetMapping(path="/content")
	public ContentBean helloWorld() {
		return new ContentBean();
	}
}
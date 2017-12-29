package org.mjjaen.rest.crudrestful.controller;

import java.util.List;

import javax.validation.Valid;

import org.mjjaen.rest.crudrestful.model.businessObject.Person;
import org.mjjaen.rest.crudrestful.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices/person")
public class Controller {
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public List<Person> retrieveAll() {
		return personService.findAll();
	}
	
	@GetMapping(path="/{id}")
	public Person retrieveOne(@PathVariable() Integer id) {
		return personService.findOne(id);
	}
	
	@PostMapping
	public Person create(@Valid @RequestBody Person person) {
		return personService.save(person);
	}
	
	@PutMapping(path="/{id}")
	public void update(@Valid @RequestBody Person person, @PathVariable(required=true) Integer id) {
		personService.update(person, id);
	}
	
	@DeleteMapping(path="/{id}")
	public void delete(@PathVariable(required=true) Integer id) {
		personService.delete(id);
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonServiceImpl(PersonService personService) {
		this.personService = personService;
	}
}

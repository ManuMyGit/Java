package org.mjjaen.rest.handlerexceptions.controller;

import java.util.List;

import javax.validation.Valid;

import org.mjjaen.rest.handlerexceptions.common.exceptions.DataNotFoundException;
import org.mjjaen.rest.handlerexceptions.model.businessObject.Person;
import org.mjjaen.rest.handlerexceptions.model.service.PersonService;
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
		List<Person> list = personService.findAll();
		if(list == null)
			throw new DataNotFoundException("Data not found");
		return list;
	}
	
	@GetMapping(path="/{id}")
	public Person retrieveOne(@PathVariable() Integer id) {
		Person person = personService.findOne(id);
		if(person == null)
			throw new DataNotFoundException(String.format("[id=%s] not found", id));
		return person;
	}
	
	@PostMapping
	public Person create(@Valid @RequestBody Person person) {
		Person savedPerson = personService.save(person);
		return savedPerson;
	}
	
	@PutMapping(path="/{id}")
	public void update(@Valid @RequestBody Person person, @PathVariable(required=true) Integer id) {
		Person updatedPerson = personService.update(person, id);
		if(updatedPerson == null)
			throw new DataNotFoundException(String.format("[id=%s] not found", id));
	}
	
	@DeleteMapping(path="/{id}")
	public void delete(@PathVariable(required=true) Integer id) {
		Person person = personService.delete(id);
		if(person == null)
			throw new DataNotFoundException(String.format("[id=%s] not found", id));
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonServiceImpl(PersonService personService) {
		this.personService = personService;
	}
}

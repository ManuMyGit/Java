package org.mjjaen.rest.handlerexceptions.model.service.impl;

import java.util.List;

import org.mjjaen.rest.handlerexceptions.model.businessObject.Person;
import org.mjjaen.rest.handlerexceptions.model.dao.PersonRepository;
import org.mjjaen.rest.handlerexceptions.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public Person findOne(Integer id) {
		return personRepository.findOne(id);
	}
	
	public Person save(Person person) {
		return personRepository.save(person);
	}
	
	public Person update(Person person, Integer id) {
		return personRepository.update(person, id);
	}
	
	public Person delete(Integer id) {
		return personRepository.delete(id);
	}

	public PersonRepository getPersonRepository() {
		return personRepository;
	}

	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
}

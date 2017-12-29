package org.mjjaen.rest.handlerexceptions.model.service;

import java.util.List;

import org.mjjaen.rest.handlerexceptions.model.businessObject.Person;

public interface PersonService {
	public List<Person> findAll();
	public Person findOne(Integer id);
	public Person save(Person person);
	public Person update(Person person, Integer id);
	public Person delete(Integer id);
}

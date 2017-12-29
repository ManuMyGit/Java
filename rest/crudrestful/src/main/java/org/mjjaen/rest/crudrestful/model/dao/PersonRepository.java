package org.mjjaen.rest.crudrestful.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.mjjaen.rest.crudrestful.model.businessObject.Person;
import org.springframework.stereotype.Component;

@SuppressWarnings("deprecation")
@Component
public class PersonRepository {
	public static List<Person> lista;
	private static Integer listaId;
	
	static {
		lista = new ArrayList<Person>();
		lista.add(new Person(1, "Firstname1", "Lastname1", "email1@domain.com", new Date("01/01/1990")));
		lista.add(new Person(2, "Firstname2", "Lastname2", "email2@domain.com", new Date("01/01/1990")));
		lista.add(new Person(3, "Firstname3", "Lastname3", "email3@domain.com", new Date("01/01/1990")));
		lista.add(new Person(4, "Firstname4", "Lastname4", "email4@domain.com", new Date("01/01/1990")));
		listaId = 4;
	}
	
	public List<Person> findAll() {
		return lista;
	}
	
	public Person findOne(Integer id) {
		Iterator<Person> iterator = lista.iterator();
		while(iterator.hasNext()) {
			Person person = iterator.next();
			if(person.getId().equals(id))
				return person;
		}
		return null;
	}
	
	public Person save(Person person) {
		if(person.getId() == null)
			person.setId(++listaId);
		lista.add(person);
		return person;
	}
	
	public Person update(Person person, Integer id) {
		Iterator<Person> iterator = lista.iterator();
		while(iterator.hasNext()) {
			Person personAux = iterator.next();
			if(personAux.getId().equals(id)) {
				personAux.setFirstName(person.getFirstName());
				personAux.setLastName(person.getLastName());
				personAux.setBirthDate(person.getBirthDate());
				personAux.setEmail(person.getEmail());
				return personAux;
			}
		}
		return null;
	}
	
	public Person delete(Integer id) {
		Iterator<Person> iterator = lista.iterator();
		while(iterator.hasNext()) {
			Person person = iterator.next();
			if(person.getId().equals(id)) {
				iterator.remove();
				return person;
			}
		}
		return null;
	}
}

package org.mjjaen.rest.crudrestful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.crudrestful.model.businessObject.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudrestfulApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test01FindAll1stWay() {
		ResponseEntity<Person[]> responseEntity = restTemplate.getForEntity("/webservices/person", Person[].class);
		List<Person> lista = Arrays.asList(responseEntity.getBody());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(lista != null && lista.size() > 0);
	}
	
	@Test
	public void test02FindAll2dnWay() {
		ResponseEntity<List<Person>> responseEntity2 = restTemplate.exchange("/webservices/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {});
		List<Person> lista2 = responseEntity2.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
		assertTrue(lista2 != null && lista2.size() > 0);
	}
	
	@Test
	public void test03FindOneExists() {
		ResponseEntity<Person> responseEntity = restTemplate.exchange("/webservices/person/1", HttpMethod.GET, null, new ParameterizedTypeReference<Person>() {});
		Person person = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(person != null);
	}
	
	@Test
	public void test04FindOneNotExists() {
		ResponseEntity<Person> responseEntity = restTemplate.getForEntity("/webservices/person/7", Person.class);
		Person person = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(person == null || person != null && person.getId() == null);
	}
	
	@Test
	public void test05CreateWithNoData() {
		ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/webservices/person", new Person(), Person.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test06CreateWithData() {
		ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/webservices/person", new Person(null, "Dior", "Eluchil", "nomail@gmail.com", new Date("01/01/2000")), Person.class);
		Person person = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(person != null);
		assertTrue(person.getId() != null);
		assertTrue(person.getFirstName() != null && person.getFirstName().equals("Dior"));
		assertTrue(person.getLastName() != null && person.getLastName().equals("Eluchil"));
		assertTrue(person.getEmail() != null && person.getEmail().equals("nomail@gmail.com"));
		assertTrue(person.getBirthDate() != null && person.getBirthDate().equals(new Date("01/01/2000")));
	}
	
	@Test
	public void test07Update() {
		ResponseEntity<Person> responseEntity = restTemplate.exchange("/webservices/person/1", HttpMethod.GET, null, new ParameterizedTypeReference<Person>() {});
		Person person1 = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		person1.setFirstName("New first name");
		restTemplate.put("/webservices/person/1", person1);
		ResponseEntity<Person> responseEntity2 = restTemplate.exchange("/webservices/person/1", HttpMethod.GET, null, new ParameterizedTypeReference<Person>() {});
		Person person2 = responseEntity2.getBody();
		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
		assertTrue(person2.getFirstName().equals("New first name"));
		assertTrue(person1.equals(person2));
	}
	
	@Test
	public void test08DeleteExists() {
		ResponseEntity<Person> responseEntity = restTemplate.exchange("/webservices/person/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<Person>() {});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void test09DeleteNotExists() {
		ResponseEntity<Person> responseEntity = restTemplate.exchange("/webservices/person/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<Person>() {});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}

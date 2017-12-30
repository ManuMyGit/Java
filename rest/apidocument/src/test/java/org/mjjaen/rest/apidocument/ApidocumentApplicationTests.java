package org.mjjaen.rest.apidocument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.apidocument.model.businessObject.Student;
import org.mjjaen.rest.apidocument.model.businessObject.Teacher;
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
public class ApidocumentApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test01GetAllTeachers() {
		ResponseEntity<Teacher[]> responseEntity = restTemplate.getForEntity("/webservices/teachers", Teacher[].class);
		List<Teacher> lista = Arrays.asList(responseEntity.getBody());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(lista != null && lista.size() > 0);
	}
	
	@Test
	public void test02GetOneTeachers() {
		ResponseEntity<Teacher> responseEntity = restTemplate.exchange("/webservices/teachers/1", HttpMethod.GET, null, new ParameterizedTypeReference<Teacher>() {});
		Teacher teacher = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(teacher != null);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test03CreateTeacher() {
		Teacher teacher = new Teacher(null, null, "Dior", "Eluchil", new Date("01/01/2000"), null, null, null);
		teacher.setStudents(Arrays.asList(new Student(null, null, "Student", "email", null, null, teacher)));
		ResponseEntity<Teacher> responseEntity = restTemplate.postForEntity("/webservices/teachers", teacher, Teacher.class);
		Teacher teacherResponse = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(teacherResponse.getId() != null);
		assertTrue(teacherResponse.getStudents() != null);
		assertTrue(teacherResponse.getStudents().size() == 1);
		assertTrue(teacherResponse.getStudents().get(0).getId() != null);
	}
	
	@Test
	public void test04UpdateTeacher() {
		ResponseEntity<Teacher> responseEntity = restTemplate.exchange("/webservices/teachers/1", HttpMethod.GET, null, new ParameterizedTypeReference<Teacher>() {});
		Teacher teacher = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(teacher != null);
		teacher.setEmail("change@gmail.com");
		restTemplate.put("/webservices/teachers/1", teacher);
		ResponseEntity<Teacher> responseEntity2 = restTemplate.exchange("/webservices/teachers/1", HttpMethod.GET, null, new ParameterizedTypeReference<Teacher>() {});
		Teacher teacher2 = responseEntity2.getBody();
		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
		assertTrue(teacher != null);
		assertTrue(teacher2.getEmail().equals(teacher.getEmail()));
	}
	
	@Test
	public void test05DeleteTeacher() {
		ResponseEntity<Teacher> responseEntity = restTemplate.exchange("/webservices/teachers/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<Teacher>() {});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Teacher> responseEntity2 = restTemplate.exchange("/webservices/teachers/1", HttpMethod.GET, null, new ParameterizedTypeReference<Teacher>() {});
		assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
	}
	
	@Test
	public void test06GetAllStudentsFromOneTeacher() {
		ResponseEntity<List<Student>> responseEntity = restTemplate.exchange("/webservices/teachers/2/students", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
		List<Student> lista = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(lista != null && lista.size() > 0);
	}
	
	@Test
	public void test07GetOneStudentFromOneTeacher() {
		ResponseEntity<Student> responseEntity = restTemplate.exchange("/webservices/teachers/2/students/5", HttpMethod.GET, null, new ParameterizedTypeReference<Student>() {});
		Student student = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(student != null);
	}
	
	@Test
	public void test08CreateStudentFromOneTeacher() {
		ResponseEntity<Teacher> responseEntity = restTemplate.exchange("/webservices/teachers/2", HttpMethod.GET, null, new ParameterizedTypeReference<Teacher>() {});
		Teacher teacher = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Student student = new Student(null, null, "asdf", "asdfa", null, null, teacher);
		ResponseEntity<Student> responseEntity2 = restTemplate.postForEntity("/webservices/teachers/2/students", student, Student.class);
		Student studentResponse = responseEntity2.getBody();
		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
		assertTrue(studentResponse.getId() != null);
		assertTrue(studentResponse.getName().equals(student.getName()));
		assertTrue(studentResponse.getEmail().equals(student.getEmail()));
	}
	
	@Test
	public void test09UpdateStudentFromOneTeacher() {
		ResponseEntity<Student> responseEntity = restTemplate.exchange("/webservices/teachers/2/students/5", HttpMethod.GET, null, new ParameterizedTypeReference<Student>() {});
		Student student = responseEntity.getBody();
		student.setName("Manolo");
		restTemplate.put("/webservices/teachers/2/students/5", student);
		ResponseEntity<Student> responseEntity2 = restTemplate.exchange("/webservices/teachers/2/students/5", HttpMethod.GET, null, new ParameterizedTypeReference<Student>() {});
		Student studentResponse = responseEntity2.getBody();
		assertTrue(student.getName().equals(studentResponse.getName()));
	}
	
	@Test
	public void test10DeleteStudentFromOneTeacher() {
		ResponseEntity<Student> responseEntity = restTemplate.exchange("/webservices/teachers/2/students/5", HttpMethod.DELETE, null, new ParameterizedTypeReference<Student>() {});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Student> responseEntity2 = restTemplate.exchange("/webservices/teachers/2/students/5", HttpMethod.GET, null, new ParameterizedTypeReference<Student>() {});
		assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
	}
}

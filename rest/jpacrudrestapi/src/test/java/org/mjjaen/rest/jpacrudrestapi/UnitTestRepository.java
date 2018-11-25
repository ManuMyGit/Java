package org.mjjaen.rest.jpacrudrestapi;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Student;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.factory.FactoryStudent;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.factory.FactoryTeacher;
import org.mjjaen.rest.jpacrudrestapi.model.repository.StudentRepository;
import org.mjjaen.rest.jpacrudrestapi.model.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest //By default, data JPA tests are transactional and roll back at the end of each test
//@Transactional(propagation = Propagation.NOT_SUPPORTED) To disable transaction management
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = "classpath:delete_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UnitTestRepository {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	private Teacher teacher1;
	private Teacher teacher2;
	private Teacher teacher3;
	private Student student1;
	private Student student2;
	private Student student3;
	
	@Before
	public void createStudents() {
		teacher1 = FactoryTeacher.createTeacher();
		teacher1.setName("Teacher1 name");
		teacher1.setEmail("teacher1@gmail.com");
		teacher1.setBirthDate(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		teacher2 = FactoryTeacher.createTeacher();
		teacher2.setName("Teacher2 name");
		teacher2.setEmail("teacher2@gmail.com");
		teacher2.setBirthDate(Date.from(LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		teacher3 = FactoryTeacher.createTeacher();
		teacher3.setName("Teacher3 name");
		teacher3.setEmail("teacher3@gmail.com");
		teacher3.setBirthDate(Date.from(LocalDate.now().minusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		student1 = FactoryStudent.createStudent();
		student1.setName("Name student 1");
		student1.setEmail("email1@gmail.com");
		student1.setTeacher(teacher1);
		student2 = FactoryStudent.createStudent();
		student2.setName("Name student 2");
		student2.setEmail("email2@gmail.com");
		student2.setTeacher(teacher1);
		student3 = FactoryStudent.createStudent();
		student3.setName("Name student 3");
		student3.setEmail("email3@gmail.com");
		student3.setTeacher(teacher1);
		
		this.entityManager.persist(teacher1);
		this.entityManager.persist(teacher2);
		this.entityManager.persist(teacher3);
		this.entityManager.persist(student1);
		this.entityManager.persist(student2);
		this.entityManager.persist(student3);
	}
	
	@Test
	public void test01FindByTeacherReturnListStudents() throws Exception {
		List<Student> students= studentRepository.findByTeacher(this.teacher1);
		assertTrue(students != null);
		assertTrue(students.size() == 3);
	}
	
	@Test
	public void test02FindByTeacherReturnEmpty() throws Exception {
		List<Student> students= studentRepository.findByTeacher(this.teacher2);
		assertTrue(students != null);
		assertTrue(students.size() == 0);
	}
	
	@Test
	public void test03FindByIdAndTeacherIdReturnStudent() throws Exception {
		Optional<Student> student = studentRepository.findByIdAndTeacherId(student1.getId(), teacher1);
		assertTrue(student.isPresent());
		assertTrue(student.get() != null);
		assertTrue(student.get().getId() == student1.getId());
		assertTrue(student.get().getTeacher().equals(teacher1));
	}
	
	@Test
	public void test04FindByIdAndTeacherIdReturnEmpty() throws Exception {
		Optional<Student> student= studentRepository.findByIdAndTeacherId(student1.getId(), teacher2);
		assertTrue(!student.isPresent());
		student= studentRepository.findByIdAndTeacherId(student1.getId() + student2.getId() + student3.getId(), teacher1);
		assertTrue(!student.isPresent());
	}
	
	@Test
	public void test05FindAllByOrderByNameAscReturnListTeachers() throws Exception {
		List<Teacher> teachers = teacherRepository.findAllByOrderByNameAsc();
		assertTrue(teachers.get(0).equals(teacher1));
	}
	
	@Test
	public void test06FindAllByOrderByEmailAscReturnListTeachers() throws Exception {
		List<Teacher> teachers = teacherRepository.findAllByOrderByEmailAsc();
		assertTrue(teachers.get(0).equals(teacher1));
	}
	
	@Test
	public void test07FindAllByOrderByBirthDateAscReturnListTeachers() throws Exception {
		List<Teacher> teachers = teacherRepository.findAllByOrderByBirthDateAsc();
		assertTrue(teachers.get(0).equals(teacher3));
	}
	
	@Test
	public void test08FindAllByOrderByIdAscReturnListTeachers() throws Exception {
		List<Teacher> teachers = teacherRepository.findAllByOrderByIdAsc();
		assertTrue(teachers.get(0).equals(teacher1));
	}

	public TestEntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(TestEntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public StudentRepository getStudentRepository() {
		return studentRepository;
	}

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public TeacherRepository getTeacherRepository() {
		return teacherRepository;
	}

	public void setTeacherRepository(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}
}

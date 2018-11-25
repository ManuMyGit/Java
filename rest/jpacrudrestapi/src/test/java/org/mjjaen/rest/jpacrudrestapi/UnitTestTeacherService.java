package org.mjjaen.rest.jpacrudrestapi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.factory.FactoryTeacher;
import org.mjjaen.rest.jpacrudrestapi.model.repository.TeacherRepository;
import org.mjjaen.rest.jpacrudrestapi.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTestTeacherService {
	@MockBean
	private TeacherRepository teacherRepository;
	
	@Autowired
	private TeacherService teacherService;
	
	private Teacher teacher1;
	private Teacher teacher2;
	private Teacher teacher3;
	
	@Before
	public void configureRepositoryMock() {
		teacher1 = FactoryTeacher.createTeacher();
		teacher1.setName("Teacher1 service name");
		teacher1.setEmail("teacher1@gmail.com");
		teacher1.setBirthDate(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		teacher1.setId(1);
		teacher2 = FactoryTeacher.createTeacher();
		teacher2.setName("Teacher2 service name");
		teacher2.setEmail("teacher2@gmail.com");
		teacher2.setBirthDate(Date.from(LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		teacher2.setId(2);
		teacher3 = FactoryTeacher.createTeacher();
		teacher3.setName("Teacher3 service name");
		teacher3.setEmail("teacher3@gmail.com");
		teacher3.setBirthDate(Date.from(LocalDate.now().minusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		teacher3.setId(3);
		List<Teacher> teachers1 = new ArrayList<Teacher>();
		List<Teacher> teachers2 = new ArrayList<Teacher>();
		List<Teacher> teachers3 = new ArrayList<Teacher>();
		List<Teacher> teachers4 = new ArrayList<Teacher>();
		List<Teacher> teachers5 = new ArrayList<Teacher>();
		teachers1.add(teacher1);
		teachers1.add(teacher2);
		teachers1.add(teacher3);
		given(this.teacherRepository.findAll()).willReturn(teachers1);
		teachers2.add(teacher1);
		teachers2.add(teacher2);
		teachers2.add(teacher3);
		given(this.teacherRepository.findAllByOrderByNameAsc()).willReturn(teachers2);
		teachers3.add(teacher1);
		teachers3.add(teacher2);
		teachers3.add(teacher3);
		given(this.teacherRepository.findAllByOrderByEmailAsc()).willReturn(teachers3);
		teachers4.add(teacher3);
		teachers4.add(teacher2);
		teachers4.add(teacher1);
		given(this.teacherRepository.findAllByOrderByBirthDateAsc()).willReturn(teachers4);
		teachers5.add(teacher1);
		teachers5.add(teacher2);
		teachers5.add(teacher3);
		given(this.teacherRepository.findAllByOrderByIdAsc()).willReturn(teachers5);
		
		given(this.teacherRepository.findById(teacher1.getId())).willReturn(Optional.of(teacher1));
		given(this.teacherRepository.findById(teacher1.getId() + teacher2.getId() + teacher3.getId())).willReturn(Optional.empty());
	}
	
	@Test
	public void test01FindAllThenReturnTeachers() {
		List<Teacher> list = teacherService.findAll(false, null);
		assertTrue(list != null);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0).equals(teacher1));
		list = teacherService.findAll(true, "nombre");
		assertTrue(list != null);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0).equals(teacher1));
		list = teacherService.findAll(true, "email");
		assertTrue(list != null);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0).equals(teacher1));
		list = teacherService.findAll(true, "birthDate");
		assertTrue(list != null);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0).equals(teacher3));
		list = teacherService.findAll(true, "other");
		assertTrue(list != null);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0).equals(teacher1));
	}
	
	@Test
	public void test02FindOneTeacherReturnTeacher() {
		Optional<Teacher> teacher = teacherService.findOneTeacher(teacher1.getId());
		assertTrue(teacher.isPresent());
		assertTrue(teacher.get().equals(teacher1));
		teacher = teacherService.findOneTeacher(teacher1.getId() + teacher2.getId() + teacher3.getId());
		assertTrue(!teacher.isPresent());
	}
	
	@Test
	public void test03SaveTeacherReturnTeacher() {
		
	}
	
	@Test
	public void test04UpdateTeacherReturnTeacher() {
		
	}
	
	@Test
	public void test05DeleteTeacherReturnTeacher() {
		
	}
	
	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
}

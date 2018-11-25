package org.mjjaen.rest.jpacrudrestapi;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.factory.FactoryTeacher;
import org.mjjaen.rest.jpacrudrestapi.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class UnitTestTeacherController {
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private TeacherService teacherService;
    
    private Teacher teacher1;
	private Teacher teacher2;
	private Teacher teacher3;
    
    @Before
    public void configureMock() {
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
		teachers1.add(teacher1);
		teachers1.add(teacher2);
		teachers1.add(teacher3);
		given(this.teacherService.findAll(false, "nombre")).willReturn(teachers1);
    }
    
    @Test
    public void test01GetAllTeachersReturnTeachers() throws Exception {
    	this.mvc.perform(get("/webservices/teachers").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(jsonPath("$", hasSize(3)))
    		.andExpect(jsonPath("$[0].name", is("Teacher1 service name")))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    	verify(teacherService, times(1)).findAll(false, "nombre");
    	verifyNoMoreInteractions(teacherService);
    }
    
	public MockMvc getMvc() {
		return mvc;
	}

	public void setMvc(MockMvc mvc) {
		this.mvc = mvc;
	}
}

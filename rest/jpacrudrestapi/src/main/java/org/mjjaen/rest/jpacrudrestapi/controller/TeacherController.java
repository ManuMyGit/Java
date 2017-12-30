package org.mjjaen.rest.jpacrudrestapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.mjjaen.rest.jpacrudrestapi.common.exceptions.DataNotFoundException;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices/teachers")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public List<Teacher> getAllTeachers(@RequestParam(required=false, defaultValue="false") boolean order, @RequestParam(required=false, defaultValue="nombre") String field) {
		List<Teacher> lista = teacherService.findAll(order, field);
		if(lista != null)
			return lista;
		else
			throw new DataNotFoundException(String.format("Data doesn't exist"));
	}
	
	@GetMapping(path="/{id}")
	public Teacher getOneTeacher(@PathVariable(required= true) Integer id) {
		Optional<Teacher> teacher = teacherService.findOneTeacher(id);
		if(teacher.isPresent()) {
			return teacher.get();
		} else
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", id));
	}
	
	@PostMapping
	public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
		return teacherService.save(teacher);
	}
	
	@PutMapping(path="/{id}")
	public void updateTeacher(@Valid @RequestBody Teacher teacher, @PathVariable(required= true) Integer id) {
		Teacher updatedTeacher = teacherService.update(teacher, id);
		if(updatedTeacher == null)
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", id));
	}
	
	@DeleteMapping(path="/{id}")
	public void deleteTeacher(@PathVariable(required= true) Integer id) {
		Optional<Teacher> deletedTeacher = teacherService.delete(id);
		if(!deletedTeacher.isPresent())
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", id));
	}

	public TeacherService getServicioteacher() {
		return teacherService;
	}

	public void setServicioteacher(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
}

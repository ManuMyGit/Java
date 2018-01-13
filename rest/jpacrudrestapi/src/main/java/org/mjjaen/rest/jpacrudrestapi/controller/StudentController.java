package org.mjjaen.rest.jpacrudrestapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.mjjaen.rest.jpacrudrestapi.common.exceptions.DataNotFoundException;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Student;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.service.StudentService;
import org.mjjaen.rest.jpacrudrestapi.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices/teachers/{id}/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public List<Resource<Student>> getAllStudents(@PathVariable(required = true) Integer id) {
		List<Resource<Student>> listaResource = new ArrayList<Resource<Student>>();
		List<Student> lista = studentService.findStudentsByTeacher(id);
		if(lista != null && lista.size() > 0) {
			Iterator<Student> iterator = lista.iterator();
			while(iterator.hasNext()) {
				Resource<Student> resource = getResourceFromStudent(iterator.next());
				listaResource.add(resource);
			}
			return listaResource;
		} else
			throw new DataNotFoundException(String.format("Data doesn't exist"));
	}
	
	@GetMapping("/{idStudent}")
	public Resource<Student> getOneStudent(@PathVariable(required = true) Integer id, @PathVariable(required = true) Integer idStudent) {
		Optional<Student> student = studentService.findStudentByTeacherAndStudent(id, idStudent);
		if(student.isPresent())
			return getResourceFromStudent(student.get());
		else
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", idStudent));
	}
	
	@PostMapping
	public Resource<Student> createStudents(@PathVariable(required = true) Integer id, @Valid @RequestBody Student student) {
		Optional<Teacher> teacher = teacherService.findOneTeacher(id);
		if(teacher.isPresent()) {
			student.setTeacher(teacher.get());
			return getResourceFromStudent(studentService.save(student));
		} else
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", id));
	}
	
	@PutMapping(path="/{idStudent}")
	public void updateStudent(@Valid @RequestBody Student student, @PathVariable(required= true) Integer id, @PathVariable(required= true) Integer idStudent) {
		if(studentService.findStudentByTeacherAndStudent(id, idStudent).isPresent()) {
			Student studentAux = studentService.update(student, idStudent);
			if(studentAux == null)
				throw new DataNotFoundException(String.format("[id=%s] doesn't exist", idStudent));
		} else
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", idStudent));
	}
	
	@DeleteMapping(path="/{idStudent}")
	public void deleteStudent(@PathVariable(required= true) Integer id, @PathVariable(required= true) Integer idStudent) {
		Optional<Student> student = studentService.delete(id, idStudent);
		if(!student.isPresent())
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", idStudent));
	}
	
	public TeacherService getServicioteacher() {
		return teacherService;
	}

	public void setServicioteacher(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	private Resource<Student> getResourceFromStudent(Student student) {
		Resource<Student> resource = new Resource<Student>(student);
		ControllerLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOneStudent(student.getTeacher().getId(), student.getId()));
		resource.add(linkToSelf.withSelfRel());
		return resource;
	}
}

package org.mjjaen.rest.jpacrudrestapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.mjjaen.rest.jpacrudrestapi.common.exceptions.DataNotFoundException;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.mjjaen.rest.jpacrudrestapi.model.dto.TeacherDto;
import org.mjjaen.rest.jpacrudrestapi.model.service.TeacherService;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/webservices/teachers")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping
	public List<Resource<Teacher>> getAllTeachers(@RequestParam(required=false, defaultValue="false") boolean order, @RequestParam(required=false, defaultValue="nombre") String field) {
		List<Resource<Teacher>> listaResource = new ArrayList<Resource<Teacher>>();
		List<Teacher> lista = teacherService.findAll(order, field);
		if(lista != null) {
			Iterator<Teacher> iterator = lista.iterator();
			while(iterator.hasNext()) {
				Resource<Teacher> resource = getResourceFromTeacher(iterator.next());
				listaResource.add(resource);
			}
			return listaResource;
		}
		else
			throw new DataNotFoundException(String.format("Data doesn't exist"));
	}
	
	@GetMapping(path="/{id}")
	public Resource<TeacherDto> getOneTeacher(@PathVariable(required= true) Integer id) {
		Optional<Teacher> teacher = teacherService.findOneTeacher(id);
		if(teacher.isPresent())
			return getResourceFromTeacher(convertToDto(teacher.get()));
		else
			throw new DataNotFoundException(String.format("[id=%s] doesn't exist", id));
	}
	
	@PostMapping
	public Resource<TeacherDto> createTeacher(@Valid @RequestBody TeacherDto teacherDto) throws ParseException {
		Teacher teacher = convertToEntity(teacherDto);
		return getResourceFromTeacher(convertToDto(teacherService.save(teacher)));
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
	
	private Resource<Teacher> getResourceFromTeacher(Teacher teacher) {
		Resource<Teacher> resource = new Resource<Teacher>(teacher);
		ControllerLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOneTeacher(teacher.getId()));
		resource.add(linkToSelf.withSelfRel());
		return resource;
	}
	
	private Resource<TeacherDto> getResourceFromTeacher(TeacherDto teacher) {
		Resource<TeacherDto> resource = new Resource<TeacherDto>(teacher);
		ControllerLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOneTeacher(teacher.getId()));
		resource.add(linkToSelf.withSelfRel());
		return resource;
	}
	
	private TeacherDto convertToDto(Teacher teacher) {
		TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
	    return teacherDto;
	}
	
	private Teacher convertToEntity(TeacherDto teacherDto) throws ParseException {
		Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
	    return teacher;
	}
}

package org.mjjaen.rest.apidocument.model.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.mjjaen.rest.apidocument.model.businessObject.Student;
import org.mjjaen.rest.apidocument.model.businessObject.Teacher;
import org.mjjaen.rest.apidocument.model.repository.TeacherRepository;
import org.mjjaen.rest.apidocument.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=1, propagation = Propagation.REQUIRED)
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Transactional(readOnly=true)
	public List<Teacher> findAll(boolean order, String field) {
		List<Teacher> lista;
		if(!order)
			lista = teacherRepository.findAll();
		else {
			switch(field) {
				case "nombre":
					lista = teacherRepository.findAllByOrderByNameAsc();
					break;
				case "email":
					lista = teacherRepository.findAllByOrderByEmailAsc();
					break;
				case "birthDate":
					lista = teacherRepository.findAllByOrderByBirthDateAsc();
					break;
				default:
					lista = teacherRepository.findAllByOrderByIdAsc();
			}
		}
		return lista;
	}
	
	@Transactional(readOnly=true)
	public Optional<Teacher> findOneTeacher(Integer id) {
		return teacherRepository.findById(id);
	}
	
	@Transactional(readOnly=false)
	public Teacher save(Teacher teacher) {
		if(teacher.getStudents() != null) {
			Iterator<Student> iterator = teacher.getStudents().iterator();
			while(iterator.hasNext()) {
				Student student = iterator.next();
				if(student.getTeacher() == null)
					student.setTeacher(teacher);
			}
		}
		return teacherRepository.save(teacher);
	}
	
	@Transactional(readOnly=false)
	public Teacher update(Teacher teacher, Integer id) {
		Teacher teacherAux = teacherRepository.findById(id).get();
		if(teacherAux != null) {
			teacherAux.setName(teacher.getName());
			teacherAux.setBirthDate(teacher.getBirthDate());
			teacherAux.setEmail(teacher.getEmail());
		}
		return teacherAux;
	}
	
	@Transactional(readOnly=false)
	public Optional<Teacher> delete(Integer id) {
		Optional<Teacher> teacher = teacherRepository.findById(id);
		if(teacher.isPresent())
			teacherRepository.deleteById(id);
		return teacher;
	}

	public TeacherRepository getTeacherRepository() {
		return teacherRepository;
	}

	public void setTeacherRepository(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}
}

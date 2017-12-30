package org.mjjaen.rest.apidocument.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.mjjaen.rest.apidocument.model.businessObject.Student;
import org.mjjaen.rest.apidocument.model.businessObject.Teacher;
import org.mjjaen.rest.apidocument.model.repository.StudentRepository;
import org.mjjaen.rest.apidocument.model.service.StudentService;
import org.mjjaen.rest.apidocument.model.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=1, propagation = Propagation.REQUIRED)
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherService teacherService;
	
	@Transactional(readOnly = true)
	public List<Student> findStudentsByTeacher(Integer id) {
		Optional<Teacher> teacher = teacherService.findOneTeacher(id);
		if(teacher.isPresent())
			return studentRepository.findByTeacher(teacher.get());
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public Optional<Student> findStudentByTeacherAndStudent(Integer idTeacher, Integer idStudent) {
		Optional<Teacher> teacher = teacherService.findOneTeacher(idTeacher);
		if(teacher.isPresent())
			return studentRepository.findByIdAndTeacherId(idStudent, teacher.get());
		else
			return null;
	}
	
	@Transactional(readOnly = false)
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	@Transactional(readOnly=false)
	public Student update(Student student, Integer id) {
		Student studentAux = studentRepository.findById(id).get();
		if(studentAux != null) {
			studentAux.setName(student.getName());
			studentAux.setEmail(student.getEmail());
		}
		return studentAux;
	}
	
	@Transactional(readOnly=false)
	public Optional<Student> delete(Integer idTeacher, Integer idStudent) {
		Optional<Student> student = findStudentByTeacherAndStudent(idTeacher, idStudent);
		if(student.isPresent()) {
			studentRepository.deleteById(idStudent);
			return student;
		}
		return null;
	}

	public StudentRepository getStudentRepository() {
		return studentRepository;
	}

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
}

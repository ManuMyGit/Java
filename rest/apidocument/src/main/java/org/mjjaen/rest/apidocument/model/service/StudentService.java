package org.mjjaen.rest.apidocument.model.service;

import java.util.List;
import java.util.Optional;

import org.mjjaen.rest.apidocument.model.businessObject.Student;

public interface StudentService {
	public List<Student> findStudentsByTeacher(Integer id);
	public Student save(Student student);
	public Optional<Student> findStudentByTeacherAndStudent(Integer idTeacher, Integer idStudent);
	public Student update(Student student, Integer id);
	public Optional<Student> delete(Integer idTeacher, Integer idStudent);
}

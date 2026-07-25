package com.StudentsManagementSystem.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.StudentsManagementSystem.entity.Student;

public interface StudentService {

	public Page<Student> getAllStudents(Pageable pageable);
	 
	public List<Student> getAllStudents();
	
	public Student saveStudent(Student student);
	
	public Student getById(int id);
	
	public void deleteById(int id);
	
	List<Student>searchStudents(String keyword);
	
	public Page<Student> searchStudents(String keyword, Pageable pageable);
	
	Student getStudentByEmail(String email);
	
	//dashboard
	long getTotalStudents();
	long getStudentsWithMobile();
	long getStudentsWithDOB();
}

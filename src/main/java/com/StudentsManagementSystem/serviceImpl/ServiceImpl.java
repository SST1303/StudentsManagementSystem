package com.StudentsManagementSystem.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StudentsManagementSystem.entity.Student;
import com.StudentsManagementSystem.entity.User;
import com.StudentsManagementSystem.repository.StudentRepository;
import com.StudentsManagementSystem.repository.UserRepository;
import com.StudentsManagementSystem.service.StudentService;

@Service
public class ServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentrepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Student> getAllStudents(){
		
		return studentrepository.findAll();
	}
	
	@Override 
	public Page<Student> getAllStudents(Pageable pageable) { 
		
		return studentrepository.findAll(pageable); 
	}
	
	@Override
	public Student saveStudent(Student student) {
		
		return studentrepository.save(student);
	}
	
	@Override
	public Student getById(int id) {
		
		return studentrepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		
		Student student = studentrepository.findById(id).orElse(null);
		
		if(student != null) {
			String email = student.getEmail();
			
			studentrepository.delete(student);
			
			User user = userRepository.findByEmail(email);
			if(user != null) {
				user.setRoles(null);
				userRepository.delete(user);
			}
		}
	}
	
	@Override
	public List<Student> searchStudents(String keyword) {
		
		return studentrepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
	            keyword, keyword, keyword);
	}
	
	
	@Override
	public Page<Student> searchStudents(String keyword, Pageable pageable){
		return studentrepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
	            keyword, keyword, keyword, pageable);
	}
	
	@Override
	public Student getStudentByEmail(String email) {
		return studentrepository.findByEmail(email);
	}
	
	//for dashboard
	@Override
	public long getTotalStudents() {
		return studentrepository.count();
	}
	
	@Override
	public long getStudentsWithMobile() {
		return studentrepository.countStudentsWithMobile();
	}
	
	@Override
	public long getStudentsWithDOB() {
		return studentrepository.countStudentsWithDOB();
	}
}

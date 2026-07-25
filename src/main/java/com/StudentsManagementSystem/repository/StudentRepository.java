package com.StudentsManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.StudentsManagementSystem.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName,
            String lastName,
            String email,
            Pageable pageable);
	
	List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
			
			String firstName,
			String lastName,
			String email);
	
	Student findByEmail(String email);
	
	//dashboard
	long countByMobileNumberIsNotNull();
	long countByDateOfBirthIsNotNull();

	
	@Query("SELECT COUNT(s) FROM Student s WHERE s.mobileNumber IS NOT NULL AND s.mobileNumber <> '' ") long countStudentsWithMobile();
	
	@Query("SELECT COUNT(s) FROM Student s WHERE s.dateOfBirth IS NOT NULL") long countStudentsWithDOB();
}


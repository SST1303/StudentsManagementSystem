package com.StudentsManagementSystem.repository;

import org.springframework.stereotype.Repository;

import com.StudentsManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	User findByResetPasswordToken(String token);

}

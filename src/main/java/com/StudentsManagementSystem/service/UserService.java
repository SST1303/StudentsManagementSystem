package com.StudentsManagementSystem.service;

import com.StudentsManagementSystem.entity.User;

public interface UserService {
	
	void saveUser(User user);
	
	User findUserByEmail(String email);

}

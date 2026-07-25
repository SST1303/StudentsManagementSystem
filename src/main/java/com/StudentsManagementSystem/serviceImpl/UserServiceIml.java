package com.StudentsManagementSystem.serviceImpl;

import java.util.Arrays;

import com.StudentsManagementSystem.entity.Role;
import com.StudentsManagementSystem.entity.User;
import com.StudentsManagementSystem.entity.Student;
import com.StudentsManagementSystem.repository.UserRepository;
import com.StudentsManagementSystem.repository.StudentRepository;
import com.StudentsManagementSystem.repository.RoleRepository;
import com.StudentsManagementSystem.service.UserService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserServiceIml implements UserService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	
	//constructor injection
	public UserServiceIml(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.studentRepository = studentRepository;
		this.roleRepository = roleRepository;
		
	}
	
	@Override
	@Transactional
	public void saveUser(User user) {
		//encrypt(encode)
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//new user is by default as a ROLE_USER
		Role role = roleRepository.findByName("ROLE_USER");
		
		if (role == null) {
			role = new Role();
			role.setName("ROLE_USER");
			role = roleRepository.save(role);
		}
		
		user.setRoles(Arrays.asList(role));
		
		userRepository.save(user);
		
		Student student = new Student();
		
		if (user.getName() != null && !user.getName().trim().isEmpty()) {
		    String[] nameParts = user.getName().trim().split("\\s+");
		    
		    if (nameParts.length == 1) {
		        student.setFirstName(nameParts[0]);
		        student.setMiddleName("");
		        student.setLastName("");
		    } 
		    else if (nameParts.length == 2) {
		        student.setFirstName(nameParts[0]);
		        student.setMiddleName("");
		        student.setLastName(nameParts[1]);
		    } 
		    else {
		        student.setFirstName(nameParts[0]);                 // Sayali
		        student.setMiddleName(nameParts[1]);                // Sudhakar 💡 (नवीन कॉलममध्ये सेव्ह होईल)
		        student.setLastName(nameParts[nameParts.length - 1]); // Thorat
		    }
		}
		
		student.setEmail(user.getEmail());
		
		studentRepository.save(student);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}

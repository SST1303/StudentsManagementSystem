package com.StudentsManagementSystem.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.StudentsManagementSystem.entity.User;
import com.StudentsManagementSystem.service.UserService;

@Controller
public class AuthController {
	
	private final UserService userService;
	
	//constructor injection
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}
	
	@PostMapping("/register/save")
	public String registration(@ModelAttribute("user") User user, Model model) {
		
		User existingUser = userService.findUserByEmail(user.getEmail());
		
		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			model.addAttribute("error", "Email already registered!");
			return "register";
		}
		
		userService.saveUser(user);
		
		return "redirect:/login?success";
	}
	
	public String listStudents(Model model, Principal principal) {
		if(principal != null) {
			String fullName = principal.getName();
			String logo = "US";
			
			if(fullName != null && !fullName.trim().isEmpty()) {
				String[] parts = fullName.split(" ");
				if(parts.length >= 2) {
					logo = ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
				}
				else {
					logo = fullName.substring(0, Math.min(fullName.length(), 2)).toUpperCase();
				}
			}
			model.addAttribute("userLogo", logo);
			model.addAttribute("userFullName", fullName);
		}
		
		return "students";
	}

}












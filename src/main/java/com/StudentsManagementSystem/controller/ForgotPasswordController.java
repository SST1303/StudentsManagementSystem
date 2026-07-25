package com.StudentsManagementSystem.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.StudentsManagementSystem.entity.User;
import com.StudentsManagementSystem.repository.UserRepository;
import com.StudentsManagementSystem.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {
	
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	
	public ForgotPasswordController(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "forgot_password";
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Model model) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			model.addAttribute("error", "We could not find any account with this email address.");
			return "forgot_password";
		}
		
		String token = UUID.randomUUID().toString();
		user.setResetPasswordToken(token);
		user.setTokenExpiryTime(LocalDateTime.now().plusMinutes(15));
		userRepository.save(user);
		
		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		String resetLink = baseUrl + "/forgot_password/reset?token=" + token;
		
		try {
			emailService.sendResetPasswordEmail(email, resetLink);
			model.addAttribute("message", "We have sent a password reset link to your email. Please check.");
		} catch (Exception e) {
			model.addAttribute("error", "Error while sending email. Please try again late.");
		}
		
		return "forgot_password";
	}
	
	@GetMapping("/forgot_password/reset")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
		User user = userRepository.findByResetPasswordToken(token);
		if(user == null || user.getTokenExpiryTime().isBefore(LocalDateTime.now())) {
			model.addAttribute("error", "Invalid or expired password reset token.");
			return "forgot_password";
		}
		
		model.addAttribute("token", token);
		return "reset_password";
	}
		
		@PostMapping("/forgot_password/reset")
		public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
			User user = userRepository.findByResetPasswordToken(token);
			if(user == null || user.getTokenExpiryTime().isBefore(LocalDateTime.now())) {
				model.addAttribute("error", "Invalid or expired password reset token.");
				return "forgot_password";
		}
			
			user.setPassword(passwordEncoder.encode(password));
			user.setResetPasswordToken(null);
			user.setTokenExpiryTime(null);
			userRepository.save(user);
			
			return "redirect:/login?resetSuccess";
	}

}

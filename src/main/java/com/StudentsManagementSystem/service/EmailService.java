package com.StudentsManagementSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendResetPasswordEmail(String toEmail, String link) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("Password Reset Request - Student Management System");
		
		String content = "Hello,\n\n"
				+ "You have requested to reset your password.\n"
				+ "Click the link below to change your password:\n"
				+ link + "\n\n"
				+ "Note: This link will expire shortly.\n"
				+ "If you did not request this, please ignore this email,";
		
		message.setText(content);
		mailSender.send(message);
	}

}

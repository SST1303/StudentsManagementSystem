package com.StudentsManagementSystem.entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	//many to many relationship with roles table
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	//default constructor
	public User() {}
	
	public User(String name, String email, String password, Collection<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	
	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	private String resetPasswordToken;

	// For added security, we can track when the token expires (optional but recommended)
	// If you use this, import java.time.LocalDateTime;
	private java.time.LocalDateTime tokenExpiryTime;

	// Don't forget to add/generate Getters and Setters for both fields!
	public String getResetPasswordToken() {
	    return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
	    this.resetPasswordToken = resetPasswordToken;
	}

	public java.time.LocalDateTime getTokenExpiryTime() {
	    return tokenExpiryTime;
	}

	public void setTokenExpiryTime(java.time.LocalDateTime tokenExpiryTime) {
	    this.tokenExpiryTime = tokenExpiryTime;
	}

}

package com.StudentsManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests((authorize) -> authorize
		    	 .requestMatchers("/register/**", "/forgot_password/**", "/forgot_password").permitAll()
		         .requestMatchers("/index","/login").permitAll()
		         .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
		         .requestMatchers("/students", "/students/edit/profile", "/students/update/profile").hasAnyRole("USER", "ADMIN")
		         .requestMatchers("/students/new", "/students/edit/{id}", "/students/delete/**").hasRole("ADMIN")
		         .anyRequest().authenticated()
		    )
            .formLogin(form -> form
                 .loginPage("/login")
                 .loginProcessingUrl("/login")
                 .defaultSuccessUrl("/students")
                 .permitAll()
            )
            .logout(logout -> logout
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/login?logout")
            	.permitAll()
            );
				
        return http.build();
	}	
}

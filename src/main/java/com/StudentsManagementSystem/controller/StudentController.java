package com.StudentsManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.security.Principal;

import com.StudentsManagementSystem.entity.Student;
import com.StudentsManagementSystem.export.StudentExcelExporter;
import com.StudentsManagementSystem.export.StudentPdfExporter;
import com.StudentsManagementSystem.export.StudentProfilePdfExporter;
import com.StudentsManagementSystem.service.StudentService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	
	@GetMapping("/home")
	public String home() {	
		return "home";  //view page html file -> home.html		
	}
	
	
	@GetMapping("/students")
	public String getAllStudents(Model model, 
	                             @RequestParam(name = "page", defaultValue = "0") int page,
	                             @RequestParam(name = "keyword", required = false) String keyword,
	                             Principal principal) {
	    
	    int pageSize = 5;
	    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("firstName"), Sort.Order.asc("middleName"), Sort.Order.asc("lastName")));
	    
	    String currentLoginEmail = principal.getName();
	    boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
	                      .getAuthorities().stream()
	                      .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
	    
	    if (isAdmin) {
	        Page<Student> studentPage;
	        if (keyword != null && !keyword.isEmpty()) {
	            studentPage = service.searchStudents(keyword, pageable);
	        } else {
	            studentPage = service.getAllStudents(pageable);
	        }
	        
	        model.addAttribute("students", studentPage.getContent()); 	
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", studentPage.getTotalPages());
	        model.addAttribute("keyword", keyword);
	        //for dashboard
	        model.addAttribute("totalStudents", service.getTotalStudents());
	        model.addAttribute("studentsWithMobile", service.getStudentsWithMobile());
	        model.addAttribute("studentsWithDOB", service.getStudentsWithDOB());	        
	        
	    } else {
	        Student student = service.getStudentByEmail(currentLoginEmail);
	        
	        if (student != null) {
	            model.addAttribute("students", Collections.singletonList(student));
	            model.addAttribute("student", student); 
	        } else {
	            model.addAttribute("students", Collections.emptyList());
	        }    	
	        
	        model.addAttribute("currentPage", 0);
	        model.addAttribute("totalPages", 1);
	    }
	    
	    return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		model.addAttribute("student", new Student());
		
		return "create-student";
	}
	
	
	@PostMapping("/students")
	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return "create-student";
		}
		
		service.saveStudent(student);
		
		redirectAttributes.addFlashAttribute("success","Student added successfully!");
		return "redirect:/students";
	}
	
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable int id, Model model) {
		
		model.addAttribute("student", service.getById(id));
		return "edit_student";
	}
	
	
	
	@PostMapping("/students/edit/{id}")
	public String updateStudents(@PathVariable int id, @Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
	        return "edit_student";
	    }

	    Student existingStudent = service.getById(id);

	    if(existingStudent == null) {
	        return "redirect:/students";
	    }

	    existingStudent.setFirstName(student.getFirstName());
	    existingStudent.setMiddleName(student.getMiddleName());
	    existingStudent.setLastName(student.getLastName());
	    existingStudent.setEmail(student.getEmail());
	    existingStudent.setMobileNumber(student.getMobileNumber());
	    existingStudent.setDateOfBirth(student.getDateOfBirth());
	    existingStudent.setAddress(student.getAddress());

	    service.saveStudent(existingStudent);

	    redirectAttributes.addFlashAttribute("success","Student updated successfully!");

	    return "redirect:/students";
	}
	

	@GetMapping("/students/edit/profile")
	public String editUserProfile(Model model, Principal principal) {
		String email = principal.getName();
		Student student = service.getStudentByEmail(email);
		model.addAttribute("student", student);
		return "update_profile";
	}
	
	@PostMapping("/students/update/profile")
	public String updateUserProfile(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
		
		Student existingStudent = service.getById(student.getId());
		
		if (existingStudent != null) {
			
			existingStudent.setMobileNumber(student.getMobileNumber());
			existingStudent.setDateOfBirth(student.getDateOfBirth());
			existingStudent.setAddress(student.getAddress());
			
			service.saveStudent(existingStudent);
		}
		
	    redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
	    return "redirect:/students";
	}
	
	
	@GetMapping("/students/delete/{id}")
	public String deleteById(@PathVariable int id, RedirectAttributes redirectAttributes) {
		
		service.deleteById(id);
		
		redirectAttributes.addFlashAttribute("success","Student delete successfully!");

		return "redirect:/students";
	}
	
	
	@GetMapping("/students/search")
	public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
		
		if(keyword == null || keyword.isBlank()) {
			model.addAttribute("students", service.getAllStudents());
		}
		else {
			model.addAttribute("students", service.searchStudents(keyword));
		}
		
		model.addAttribute("keyword", keyword);
		
		return "students";
	}

	//for admin download pdf
	@GetMapping("/students/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws Exception {

	    response.setContentType("application/pdf");

	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=students.pdf";
	    response.setHeader(headerKey, headerValue);

	    List<Student> listStudents = service.getAllStudents();
	    
	    listStudents.sort(Comparator.comparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER));

	    StudentPdfExporter exporter = new StudentPdfExporter(listStudents);

	    exporter.export(response);

	}
	
	//for admin download excel
	@GetMapping("/students/export/excel")
	public void exportToExcel(HttpServletResponse response) throws Exception {

	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=students.xlsx";
	    response.setHeader(headerKey, headerValue);

	    List<Student> listStudents = service.getAllStudents();
	    
	    listStudents.sort(Comparator.comparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER));

	    StudentExcelExporter excelExporter = new StudentExcelExporter(listStudents);

	    excelExporter.export(response);
	}
	
	//for user download pdf
	@GetMapping("/students/profile/pdf")
	public void exportMyProfile(HttpServletResponse response, Principal principal) throws Exception {

		String email = principal.getName();
	    Student student = service.getStudentByEmail(email);
	    
	    if (student == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Student profile not found.");
	        return;
	    }

	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=my_profile_" + student.getFirstName() + ".pdf");

	    StudentProfilePdfExporter exporter = new StudentProfilePdfExporter(Collections.singletonList(student));
	    
	    exporter.export(response);
	}
	
	
	//for print student data
	@GetMapping("/students/print/{id}")
	public String printStudent(@PathVariable int id, Model model) {

	    Student student = service.getById(id);

	    model.addAttribute("student", student);

	    return "print_student";
	}
	
	
}












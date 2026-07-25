package com.StudentsManagementSystem.export;

import java.io.IOException;
import java.util.List;
import java.awt.Color;

import com.StudentsManagementSystem.entity.Student;
import jakarta.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;


public class StudentProfilePdfExporter {

	private List<Student> students;

    public StudentProfilePdfExporter(List<Student> students) {
        this.students = students;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

        Paragraph title = new Paragraph("STUDENT PROFILE", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        LineSeparator line = new LineSeparator();
        line.setLineColor(Color.LIGHT_GRAY);
        document.add(line);
        document.add(new Paragraph(" ")); 

        if (students != null && !students.isEmpty()) {
            
        	Student student = students.get(0); 

            Paragraph sectionHeader = new Paragraph("Personal Information", sectionFont);
            sectionHeader.setSpacingAfter(15);
            document.add(sectionHeader);

            // Full Name
            String middle = (student.getMiddleName() == null || student.getMiddleName().isBlank()) ? "" : student.getMiddleName() + " ";
            String fullName = student.getFirstName() + " " + middle + student.getLastName();
            addProfileField(document, "Full Name: ", fullName, labelFont, valueFont);

            // Email
            addProfileField(document, "Email Address: ", student.getEmail(), labelFont, valueFont);

            //Mobile
            String mobile = (student.getMobileNumber() == null) ? "N/A" : student.getMobileNumber();
            addProfileField(document, "Mobile Number: ", mobile, labelFont, valueFont);

            //DOB
            String dob = (student.getDateOfBirth() == null) ? "N/A" : student.getDateOfBirth().toString();
            addProfileField(document, "Date of Birth: ", dob, labelFont, valueFont);

            //Address
            String address = (student.getAddress() == null || student.getAddress().isBlank()) ? "N/A" : student.getAddress();
            addProfileField(document, "Address: ", address, labelFont, valueFont);
        }

        document.close();
    }

    private void addProfileField(Document document, String label, String value, Font labelFont, Font valueFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new com.lowagie.text.Chunk(label, labelFont));
        p.add(new com.lowagie.text.Chunk(value, valueFont));
        p.setSpacingAfter(8); 
        document.add(p);
    }
}
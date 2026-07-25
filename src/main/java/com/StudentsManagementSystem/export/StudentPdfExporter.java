package com.StudentsManagementSystem.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.StudentsManagementSystem.entity.Student;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class StudentPdfExporter {
    private List<Student> listStudents;

    public StudentPdfExporter(List<Student> listStudents) {
        this.listStudents = listStudents;
    }

    private void writeTitle(Document document) throws Exception {

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Student Management System", font);

        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));
    }

    private void writeTable(Document document) throws Exception {

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        addTableHeader(table);

        int serialNo = 1;

        for(Student student : listStudents) {

            table.addCell(String.valueOf(serialNo++));

            table.addCell(student.getFirstName());
            table.addCell(student.getMiddleName());
            table.addCell(student.getLastName());
            table.addCell(student.getEmail());
            table.addCell(student.getMobileNumber());

            table.addCell(student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "" );

            table.addCell(student.getAddress());
        }

        document.add(table);

    }

    private void addTableHeader(PdfPTable table) {

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String[] headers = {
        		"ID",
                "First Name",
                "Middle Name",
                "Last Name",
                "Email",
                "Mobile",
                "DOB",
                "Address"
        };

        for(String header : headers) {

            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase(header, font));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell);

        }
    }

    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);

        try {

            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            writeTitle(document);
            writeTable(document);
            document.close();

        } catch(Exception e) {

            e.printStackTrace();

        }
    }
}
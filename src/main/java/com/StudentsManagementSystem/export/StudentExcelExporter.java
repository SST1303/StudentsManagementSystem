package com.StudentsManagementSystem.export;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.StudentsManagementSystem.entity.Student;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class StudentExcelExporter {

	private List<Student> listStudents;

    public StudentExcelExporter(List<Student> listStudents) {
        this.listStudents = listStudents;
    }
    
    private void writeHeaderLine(Sheet sheet) {

        Row row = sheet.createRow(0);

        String[] headers = {
                "ID",
                "First Name",
                "Middle Name",
                "Last Name",
                "Email",
                "Mobile Number",
                "Date Of Birth",
                "Address"
        };


        for(int i=0; i<headers.length; i++) {

            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);

        }
    }
    
    private void writeDataLines(Sheet sheet) {

        int rowIndex = 1;
        int serialNo = 1;

        for (Student student : listStudents) {

            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(serialNo++);   // Serial Number

            row.createCell(1).setCellValue(student.getFirstName());
            row.createCell(2).setCellValue(student.getMiddleName());
            row.createCell(3).setCellValue(student.getLastName());
            row.createCell(4).setCellValue(student.getEmail());
            row.createCell(5).setCellValue(student.getMobileNumber());

            row.createCell(6).setCellValue(student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "" );

            row.createCell(7).setCellValue(student.getAddress());
        }
    }
    
    public void export(HttpServletResponse response) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("Students");

            writeHeaderLine(sheet);
            writeDataLines(sheet);

            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } finally {
            workbook.close();
        }
    }
}

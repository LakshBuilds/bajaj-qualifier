package com.bajajfinserv.healthqualifier.service;

import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {
    
    public String solveSqlProblem(String regNo) {
        // Extract last two digits from registration number
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);
        
        // Determine question type based on last two digits
        if (lastTwoDigitsInt % 2 == 1) {
            // Odd number - Question 1
            return solveQuestion1();
        } else {
            // Even number - Question 2
            return solveQuestion2();
        }
    }
    
    public String getQuestionType(String regNo) {
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);
        
        return lastTwoDigitsInt % 2 == 1 ? "Question 1" : "Question 2";
    }
    
    private String solveQuestion1() {
        // Based on the hospital management system described in the PDF
        // Question 1: Find patients who have appointments with doctors from the Cardiology department
        // and have had treatments with cost greater than 5000
        return "SELECT DISTINCT p.patient_id, p.patient_name, p.age, p.gender " +
               "FROM patients p " +
               "JOIN appointments a ON p.patient_id = a.patient_id " +
               "JOIN doctors d ON a.doctor_id = d.doctor_id " +
               "JOIN treatments t ON p.patient_id = t.patient_id " +
               "WHERE d.department = 'Cardiology' " +
               "AND t.cost > 5000 " +
               "ORDER BY p.patient_name";
    }
    
    private String solveQuestion2() {
        // Question 2: Find departments with average salary > 60000
        // This is a placeholder - replace with actual solution from Google Drive
        return "SELECT department, COUNT(*) as employee_count, AVG(salary) as avg_salary FROM employees GROUP BY department HAVING AVG(salary) > 60000 ORDER BY avg_salary DESC";
    }
} 
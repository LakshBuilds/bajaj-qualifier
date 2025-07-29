package com.bajajfinserv.healthqualifier.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlProblemSolverTest {

    @Autowired
    private SqlProblemSolver sqlProblemSolver;

    @Test
    void testQuestionTypeDetermination() {
        // Test odd registration number (should be Question 1)
        String oddRegNo = "REG12347";
        String questionType1 = sqlProblemSolver.getQuestionType(oddRegNo);
        assertEquals("Question 1", questionType1);

        // Test even registration number (should be Question 2)
        String evenRegNo = "REG12348";
        String questionType2 = sqlProblemSolver.getQuestionType(evenRegNo);
        assertEquals("Question 2", questionType2);
    }

    @Test
    void testSqlProblemSolving() {
        // Test solving for odd registration number
        String oddRegNo = "REG12347";
        String sqlQuery1 = sqlProblemSolver.solveSqlProblem(oddRegNo);
        assertNotNull(sqlQuery1);
        assertTrue(sqlQuery1.contains("SELECT"));
        assertTrue(sqlQuery1.contains("employees"));

        // Test solving for even registration number
        String evenRegNo = "REG12348";
        String sqlQuery2 = sqlProblemSolver.solveSqlProblem(evenRegNo);
        assertNotNull(sqlQuery2);
        assertTrue(sqlQuery2.contains("SELECT"));
        assertTrue(sqlQuery2.contains("employees"));
    }
} 
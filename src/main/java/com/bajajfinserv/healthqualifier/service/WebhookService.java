package com.bajajfinserv.healthqualifier.service;

import com.bajajfinserv.healthqualifier.dto.SolutionRequest;
import com.bajajfinserv.healthqualifier.dto.WebhookRequest;
import com.bajajfinserv.healthqualifier.dto.WebhookResponse;
import com.bajajfinserv.healthqualifier.entity.SqlResult;
import com.bajajfinserv.healthqualifier.repository.SqlResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String TEST_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    private final RestTemplate restTemplate;
    private final SqlProblemSolver sqlProblemSolver;
    private final SqlResultRepository sqlResultRepository;
    
    public WebhookService(RestTemplate restTemplate, SqlProblemSolver sqlProblemSolver, SqlResultRepository sqlResultRepository) {
        this.restTemplate = restTemplate;
        this.sqlProblemSolver = sqlProblemSolver;
        this.sqlResultRepository = sqlResultRepository;
    }
    
    public void processWebhookFlow() {
        try {
            logger.info("Starting webhook flow...");
            
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
            
            // Step 2: Solve SQL problem
            String regNo = "2210991837"; // Updated with actual registration number
            String questionType = sqlProblemSolver.getQuestionType(regNo);
            String finalQuery = sqlProblemSolver.solveSqlProblem(regNo);
            logger.info("SQL problem solved for {}: {}", questionType, finalQuery);
            
            // Step 3: Store result
            SqlResult sqlResult = new SqlResult(regNo, questionType, finalQuery, webhookResponse.getWebhook(), webhookResponse.getAccessToken());
            sqlResultRepository.save(sqlResult);
            logger.info("SQL result stored in database");
            
            // Step 4: Submit solution
            submitSolution(finalQuery, webhookResponse.getAccessToken());
            logger.info("Solution submitted successfully");
            
        } catch (Exception e) {
            logger.error("Error in webhook flow: ", e);
        }
    }
    
    private WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest("Laksh Mahajan", "2210991837", "laksh1837.be22@chitkara.edu.in");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
            GENERATE_WEBHOOK_URL, 
            entity, 
            WebhookResponse.class
        );
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate webhook. Status: " + response.getStatusCode());
        }
    }
    
    private void submitSolution(String finalQuery, String accessToken) {
        SolutionRequest request = new SolutionRequest(finalQuery);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        
        HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            TEST_WEBHOOK_URL,
            entity,
            String.class
        );
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to submit solution. Status: " + response.getStatusCode());
        }
        
        logger.info("Solution submission response: {}", response.getBody());
    }
} 
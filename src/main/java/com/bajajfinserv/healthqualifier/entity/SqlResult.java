package com.bajajfinserv.healthqualifier.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sql_results")
public class SqlResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String regNo;
    private String questionType;
    private String finalQuery;
    private String webhookUrl;
    private String accessToken;
    
    public SqlResult() {}
    
    public SqlResult(String regNo, String questionType, String finalQuery, String webhookUrl, String accessToken) {
        this.regNo = regNo;
        this.questionType = questionType;
        this.finalQuery = finalQuery;
        this.webhookUrl = webhookUrl;
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
} 
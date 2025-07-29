package com.bajajfinserv.healthqualifier;

import com.bajajfinserv.healthqualifier.service.WebhookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HealthQualifierApplication {

    private final WebhookService webhookService;

    public HealthQualifierApplication(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthQualifierApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        webhookService.processWebhookFlow();
    }
} 
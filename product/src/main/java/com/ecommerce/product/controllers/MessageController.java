package com.ecommerce.product.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {

    @Value("${app.message}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name = "retryBreaker", fallbackMethod = "fallbackMessage")
    public String getMessage() {
        return message;
    }

    public String fallbackMessage(Exception e) {
        return "Hello, this is a fallback message from the Product Service!";
    }
}

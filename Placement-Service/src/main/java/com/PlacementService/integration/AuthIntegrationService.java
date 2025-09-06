package com.PlacementService.integration;

import org.springframework.stereotype.Service;

import com.PlacementService.Dto.RegisterAuthUserRequest;
import com.PlacementService.client.AuthServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class AuthIntegrationService {

    private final AuthServiceClient authServiceClient;

    public AuthIntegrationService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Retry(name = "authRetry", fallbackMethod = "fallbackRegisterUser")
    @CircuitBreaker(name = "authCircuitBreaker", fallbackMethod = "fallbackRegisterUser")
    public String registerUser(RegisterAuthUserRequest request) {
        return authServiceClient.registerUser(request).getBody();
    }

    public String fallbackRegisterUser(RegisterAuthUserRequest request, Throwable t) {
        return "⚠ Auth Service unavailable. Please try again later.";
    }
}

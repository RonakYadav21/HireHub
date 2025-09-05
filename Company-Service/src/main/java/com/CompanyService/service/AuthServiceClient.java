package com.CompanyService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.CompanyService.Model.RegisterAuthUserRequest;


@FeignClient(name ="AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/auth/register")
    ResponseEntity<String> registerUser(@RequestBody RegisterAuthUserRequest request);
}

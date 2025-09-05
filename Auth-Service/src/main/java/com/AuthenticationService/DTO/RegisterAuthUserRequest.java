package com.AuthenticationService.DTO;

//com.AuthenticationService.DTO.RegisterAuthUserRequest.java

import lombok.Data;

@Data
public class RegisterAuthUserRequest {
 private String username;
 private String password;
 private String role;
}

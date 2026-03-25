package com.AuthenticationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthenticationService.DTO.JwtResponse;
import com.AuthenticationService.DTO.LoginRequest;
import com.AuthenticationService.DTO.RegisterAuthUserRequest;
import com.AuthenticationService.Model.Users;
import com.AuthenticationService.Repository.UserRepository;
import com.AuthenticationService.UserService.CustomUserDetailsService;
import com.AuthenticationService.util.JwtUtil;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import  com.AuthenticationService.CustomException.AccountStatusException;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil  jwtUtil;
    
    @Autowired
    UserRepository userrepo;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");

        } catch (InternalAuthenticationServiceException e) {
            // 🔥 extract your custom message
            Throwable cause = e.getCause();

            if (cause instanceof AccountStatusException) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(cause.getMessage());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication error");
        }

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
    
    @PostMapping("/register")  // will be called when a user register in their micorservice  by that microservice
    public ResponseEntity<?> register(@RequestBody RegisterAuthUserRequest request) {
        if (userrepo.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        Users user = Users.builder() //no need to use setter method because builder is provided by lombok
            .username(request.getUsername())
            .password(request.getPassword())
            .role(request.getRole())
            .build();

        userrepo.save(user);

        return ResponseEntity.ok("User registered in AuthService");
    }
}


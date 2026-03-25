package com.AuthenticationService.UserService;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import  com.AuthenticationService.CustomException.AccountStatusException;
import com.AuthenticationService.Model.Users;
import com.AuthenticationService.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 🔹 Status validation
        String status = user.getStatus();

        if (status != null) {

        	if (status.equalsIgnoreCase("PENDING")) {
        	    throw new AccountStatusException("Your account is pending admin approval.");
        	}

        	if (status.equalsIgnoreCase("REJECTED")) {
        	    throw new AccountStatusException("Your account has been rejected by admin.");
        	}
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
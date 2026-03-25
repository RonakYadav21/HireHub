package com.AuthenticationService.CustomException;

public class AccountStatusException extends RuntimeException {
    public AccountStatusException(String message) {
        super(message);
    }
}
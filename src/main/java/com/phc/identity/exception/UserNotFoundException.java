package com.phc.identity.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

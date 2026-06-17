package com.example.bfhl.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
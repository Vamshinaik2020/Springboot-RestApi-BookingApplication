package com.springboot.springbootrestapi.exception;

public class CustomerAgeNotValidException extends RuntimeException {
    public CustomerAgeNotValidException(String message) {
        super(message);
    }
}

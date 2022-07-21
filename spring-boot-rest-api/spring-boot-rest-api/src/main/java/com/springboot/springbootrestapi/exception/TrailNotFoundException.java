package com.springboot.springbootrestapi.exception;

public class TrailNotFoundException extends RuntimeException {
    public TrailNotFoundException(String message) {
        super(message);
    }
}

package com.springboot.springbootrestapi.exception;

public class CustomerAgeNotValidException extends Throwable {
    public CustomerAgeNotValidException(String message) {
        super(message);
    }
}

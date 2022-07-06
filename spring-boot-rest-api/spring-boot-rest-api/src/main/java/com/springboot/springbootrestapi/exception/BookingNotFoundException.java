package com.springboot.springbootrestapi.exception;

public class BookingNotFoundException extends Throwable {
    public BookingNotFoundException(String message) {
        super(message);
    }
}

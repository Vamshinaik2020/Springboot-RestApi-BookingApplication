package com.springboot.springbootrestapi.exception;

public class FavouriteAlreadyPresentException extends RuntimeException{
    public FavouriteAlreadyPresentException(String message) {
        super(message);
    }
}

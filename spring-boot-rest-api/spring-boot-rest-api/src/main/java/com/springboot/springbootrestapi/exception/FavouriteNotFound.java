package com.springboot.springbootrestapi.exception;

public class FavouriteNotFound extends RuntimeException{
    public FavouriteNotFound(String message) {
        super(message);
    }
}

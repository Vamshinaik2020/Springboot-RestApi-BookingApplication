package com.springboot.springbootrestapi.exception;

import com.springboot.springbootrestapi.model.Trail;

import java.util.Optional;

public class TrailNotFoundException extends Throwable {
    public TrailNotFoundException(String trailId) {

    }
}

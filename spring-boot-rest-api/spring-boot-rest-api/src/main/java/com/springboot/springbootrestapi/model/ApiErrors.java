package com.springboot.springbootrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrors {
    String message;
    List<String> details;
    HttpStatus status;
    LocalDateTime timestamp;
}

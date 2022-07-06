package com.springboot.springbootrestapi.DTO;

import lombok.Data;

@Data
public class BookingDTO {
    private String customerName;
    private long customerAge;
    private String gender;
    private String trailId;
    public BookingDTO(String customerName, long customerAge, String gender, String trailId) {
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.gender = gender;
        this.trailId = trailId;
    }
}

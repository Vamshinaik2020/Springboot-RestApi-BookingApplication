package com.springboot.springbootrestapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection="Bookings")
public class Booking {

    @Id
    private String id;
    private String customerName;
    private long customerAge;
    private String gender;
    private String trailId;
    public Booking(String customerName, long customerAge, String gender, String trailId) {
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.gender = gender;
        this.trailId = trailId;
    }
}






package com.springboot.springbootrestapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Trails")
public class Trail {
    @Id
    private String id;
    private String name;
    private String startAt;
    private String endAt;
    private int minimumAge;
    private int maximumAge;
    private int unitPrice;

    public Trail(String name, String startAt, String endAt, int minimumAge, int maximumAge, int unitPrice) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.unitPrice = unitPrice;
    }
}

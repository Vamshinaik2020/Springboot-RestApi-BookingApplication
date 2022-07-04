package com.springboot.springbootrestapi.DTO;

import lombok.Data;

@Data
public class TrailDTO {
    private String title;
    private String startAt;
    private String endAt;
    private  int minimumAge;
    private int maximumAge;
    private int unitPrice;

    public TrailDTO(String title, String startAt, String endAt, int minimumAge, int maximumAge, int unitPrice) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.unitPrice = unitPrice;
    }
}

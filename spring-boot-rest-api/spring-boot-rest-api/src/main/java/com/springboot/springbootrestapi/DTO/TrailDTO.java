package com.springboot.springbootrestapi.DTO;
import lombok.Data;

@Data
public class TrailDTO {
    private String name;
    private String startAt;
    private String endAt;
    private  int minimumAge;
    private int maximumAge;
    private int unitPrice;

    public TrailDTO(String name, String startAt, String endAt, int minimumAge, int maximumAge, int unitPrice) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.unitPrice = unitPrice;
    }
}

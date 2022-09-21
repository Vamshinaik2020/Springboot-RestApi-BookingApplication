package com.springboot.springbootrestapi.DTO;

import com.springboot.springbootrestapi.validations.NameValidation;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Min;

@Data
public class TrailDTO {
    @NameValidation
    private String name;
    @NotNull
    private String startAt;
    @NotNull
    private String endAt;
    @Min(1)
    private int minimumAge;
    @Min(1)
    private int maximumAge;
    @Min(1)
    private int unitPrice;

    public TrailDTO(String name, @NotNull String startAt, @NotNull String endAt, int minimumAge, int maximumAge, int unitPrice) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.unitPrice = unitPrice;
    }
}

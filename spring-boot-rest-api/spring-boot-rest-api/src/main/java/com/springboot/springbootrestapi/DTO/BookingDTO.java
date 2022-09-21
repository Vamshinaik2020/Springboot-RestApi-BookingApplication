package com.springboot.springbootrestapi.DTO;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class BookingDTO {
    @NotNull
    private String customerName;
    private long customerAge;
    @NotNull
    private String gender;
    @NotNull
    private String trailId;

    public BookingDTO(@NotNull String customerName, long customerAge, @NotNull String gender, @NotNull String trailId) {
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.gender = gender;
        this.trailId = trailId;
    }
}

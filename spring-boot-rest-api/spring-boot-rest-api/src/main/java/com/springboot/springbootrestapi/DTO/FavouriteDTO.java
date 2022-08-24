package com.springboot.springbootrestapi.DTO;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class FavouriteDTO {
    @NotNull
    private String trailId;
    @NotNull
    private String customerName;

    public FavouriteDTO(@NotNull String trailId, @NotNull String customerName) {
        this.trailId = trailId;
        this.customerName = customerName;
    }
}

package com.springboot.springbootrestapi.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Favourites")
public class Favourite {

    @Id
    private String id;
    private String trailId;
    private String customerName;

    public Favourite(String trailId, String customerName) {
        this.trailId = trailId;
        this.customerName = customerName;
    }
}

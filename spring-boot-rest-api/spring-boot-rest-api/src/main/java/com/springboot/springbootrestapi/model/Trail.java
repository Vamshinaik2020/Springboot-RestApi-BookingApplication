package com.springboot.springbootrestapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection="Trails")

public class Trail {

   @Id
    private String id;

    private String title;
    private long startAt;
    private long endAt;
    private int minimumAge;
    private int maximumAge;
    private long unitPrice;

 public Trail(String title, long startAt, long endAt, int minimumAge, int maximumAge, long unitPrice) {
  this.title = title;
  this.startAt = startAt;
  this.endAt = endAt;
  this.minimumAge = minimumAge;
  this.maximumAge = maximumAge;
  this.unitPrice = unitPrice;
 }
}

package com.springboot.springbootrestapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="Trails")
public class Trail {

    @Id
    private String id;
    private String title;
    private String startAt;
    private String endAt;
    private int minimumAge;
    private int maximumAge;
    private int unitPrice;
 public Trail(String title, String startAt, String endAt, int minimumAge, int maximumAge, int unitPrice) {
  this.title = title;
  this.startAt = startAt;
  this.endAt = endAt;
  this.minimumAge = minimumAge;
  this.maximumAge = maximumAge;
  this.unitPrice = unitPrice;
 }
}

package com.springboot.springbootrestapi.Repository;

import com.springboot.springbootrestapi.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrailRepository extends MongoRepository <Trail, String>{
}

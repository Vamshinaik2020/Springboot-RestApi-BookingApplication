package com.springboot.springbootrestapi.Repository;

import com.springboot.springbootrestapi.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends MongoRepository <Trail, String>{
}

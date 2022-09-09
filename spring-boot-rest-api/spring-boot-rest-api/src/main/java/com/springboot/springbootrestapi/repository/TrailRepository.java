package com.springboot.springbootrestapi.repository;

import com.springboot.springbootrestapi.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailRepository extends MongoRepository <Trail, String> {
    List<Trail> findTrailByName(String name);
}

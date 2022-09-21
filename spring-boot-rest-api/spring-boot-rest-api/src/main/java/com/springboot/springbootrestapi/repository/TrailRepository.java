package com.springboot.springbootrestapi.repository;

import com.springboot.springbootrestapi.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailRepository extends MongoRepository <Trail, String> {
    @Query("{ 'name': { '$regex':'?0', '$options':'i' }}")
    List<Trail> findTrailByName(String name);
}

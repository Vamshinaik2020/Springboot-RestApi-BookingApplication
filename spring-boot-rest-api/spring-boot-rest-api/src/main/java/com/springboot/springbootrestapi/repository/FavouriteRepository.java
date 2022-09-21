package com.springboot.springbootrestapi.repository;

import com.springboot.springbootrestapi.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite, String> {

    Optional<Favourite> findByTrailId(String trailId);
}

package com.springboot.springbootrestapi.repository;

import com.springboot.springbootrestapi.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findBookingsByCustomerName(String customerName);
}

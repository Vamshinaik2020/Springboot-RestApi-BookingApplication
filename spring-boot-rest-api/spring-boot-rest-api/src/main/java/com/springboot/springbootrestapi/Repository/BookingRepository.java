package com.springboot.springbootrestapi.Repository;

import com.springboot.springbootrestapi.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> getAllBookingByCustomerName(String customerName);

}

package com.springboot.springbootrestapi.Service;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.Repository.BookingRepository;
import com.springboot.springbootrestapi.Repository.TrailRepository;
import com.springboot.springbootrestapi.exception.BookingIdNotFoundException;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class TrekBookingService {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private BookingRepository bookingRepository;


    public List<Trail> getAllTrails() {
        return trailRepository.findAll();
    }


    public Booking makeBooking(BookingDTO bookingDTO) throws TrailNotFoundException, BookingIdNotFoundException {
        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());

        if (trail.isEmpty()) throw new TrailNotFoundException("Trail Not Found");

        Trail trail1 = trail.get();

        if (bookingDTO.getCustomerAge() >= trail1.getMinimumAge() && bookingDTO.getCustomerAge() <= trail1.getMaximumAge()) {
            Booking booking = new Booking(bookingDTO.getCustomerName(), bookingDTO.getCustomerAge(), bookingDTO.getGender(), bookingDTO.getTrailId());

            return bookingRepository.save(booking);
        }
        else throw new BookingIdNotFoundException("Customer Age Not Valid");

    }


    public List<Booking> getAllBookingByCustomerName(String customerName) {

        return bookingRepository.getAllBookingByCustomerName(customerName);


    }

}





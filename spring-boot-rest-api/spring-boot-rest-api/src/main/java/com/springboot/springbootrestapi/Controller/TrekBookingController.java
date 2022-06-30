package com.springboot.springbootrestapi.Controller;


import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.Service.TrekBookingService;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValid;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrekBookingController {

    @Autowired
    private TrekBookingService trekBookingService;


    @GetMapping("/trails")
    public  List<Trail> getTrails() {
        return trekBookingService.getAllTrails();

    }


   @PostMapping("/makebooking")
    public ResponseEntity<Booking> makeBooking (@RequestBody BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValid {
        Booking booking = trekBookingService.makeBooking(bookingDTO);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);

    }



    @GetMapping("/{customerName}")
    public List<Booking> getAllBookingByCustomerName(@PathVariable("customerName") String customerName) {
        return trekBookingService.getAllBookingByCustomerName(customerName);

    }


}





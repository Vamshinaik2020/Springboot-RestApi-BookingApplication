package com.springboot.springbootrestapi.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.Service.TrekBookingService;
import com.springboot.springbootrestapi.exception.BookingIdNotFoundException;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Booking> makeBooking (@RequestBody BookingDTO bookingDTO) throws BookingIdNotFoundException, TrailNotFoundException {
        Booking booking = trekBookingService.makeBooking(bookingDTO);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);

    }



    @GetMapping("/makebooking/byCustomerName/{customerName}")
    public ResponseEntity<Booking> getBookingByCustomerName(@PathVariable(value = "customerName") BookingDTO customerName)
            throws TrailNotFoundException, BookingIdNotFoundException {
        return new ResponseEntity<>(trekBookingService.getBookingByCustomerName(customerName), HttpStatus.OK);
    }


}


   /* public ResponseEntity<Void> makeBookingForTrail(@PathVariable String trailId,
                                                    @RequestBody Booking booking) throws TrailNotFoundException {

        Booking booking1 = trekBookingService.makeBooking();

        if(booking1 == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(booking.getId()).toUri();
        return ResponseEntity.created(location).build();
    } */




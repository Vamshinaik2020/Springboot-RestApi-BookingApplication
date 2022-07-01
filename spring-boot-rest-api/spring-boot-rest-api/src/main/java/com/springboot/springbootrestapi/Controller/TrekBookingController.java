package com.springboot.springbootrestapi.Controller;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.Service.TrekBookingService;
import com.springboot.springbootrestapi.exception.BookingNotFoundException;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValid;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class TrekBookingController {
    @Autowired
    private TrekBookingService trekBookingService;

    @GetMapping("/trails")
    public List<Trail> getTrails() {
        return trekBookingService.getAllTrails();
    }

    @PostMapping("/trails")
    public ResponseEntity<Trail> addNewTrail(@RequestBody Trail trail) {
        Trail newTrail = trekBookingService.addNewTrail(trail);
        return new ResponseEntity<>(trail, HttpStatus.CREATED);
    }

    @PutMapping("trails/{trailId}")
    public ResponseEntity<Trail> updateTrailById(@PathVariable("trailId") String trailId, @RequestBody Trail trail) throws TrailNotFoundException {
        return new ResponseEntity<>(trekBookingService.updateTrailById(trail, trailId), HttpStatus.CREATED);
    }

    @DeleteMapping("/trails/{trailId}")
    public void deleteTrailById(@PathVariable String trailId) {
        trekBookingService.deleteTrailById(trailId);
    }

    @PostMapping("/makebooking")
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValid {
        Booking booking = trekBookingService.makeBooking(bookingDTO);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/booking")
    public List<Booking> getAllBookingByCustomerName(@RequestParam String customerName) {
        return trekBookingService.getAllBookingByCustomerName(customerName);
    }

    @GetMapping("/booking/{bookingId}")
    public Optional<Booking> getBookingById(@PathVariable("bookingId") String bookingId) {
        return trekBookingService.getBookingById(bookingId);
    }

    @PutMapping("booking/{bookingId}")
    public Booking updateBookingById(@RequestBody Booking booking, @PathVariable("bookingId") String bookingId) throws BookingNotFoundException, CustomerAgeNotValid {
        return trekBookingService.updateBookingById(booking, bookingId);
    }

    @DeleteMapping("/booking/{bookingId}")
    public void deleteBookingById(@PathVariable String bookingId) {
        trekBookingService.deleteBookingById(bookingId);
    }
}






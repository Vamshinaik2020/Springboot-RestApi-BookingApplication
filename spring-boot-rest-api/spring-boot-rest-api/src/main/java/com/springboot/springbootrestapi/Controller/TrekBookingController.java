package com.springboot.springbootrestapi.Controller;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.DTO.TrailDTO;
import com.springboot.springbootrestapi.Service.TrekBookingService;
import com.springboot.springbootrestapi.exception.BookingNotFoundException;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValidException;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TrekBookingController {
    @Autowired
    private TrekBookingService trekBookingService;

    @GetMapping("/trails")
    public ResponseEntity<List<Trail>> getTrails() {
        List <Trail> trail = trekBookingService.getAllTrails();
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get all trails");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(trail);
    }

    @GetMapping("/trails/{trailId}")
    public ResponseEntity<Trail> getTrailById(@PathVariable String trailId) throws TrailNotFoundException {
        Trail trail = trekBookingService.getTrailById(trailId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(trail);
    }

    @PostMapping("/trails")
    public ResponseEntity<Trail> addNewTrail(@RequestBody TrailDTO trailDTO) {
        Trail trail = trekBookingService.addNewTrail(trailDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Adding a new trail");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(trail);
    }

    @PutMapping("trails/{trailId}")
    public ResponseEntity<Trail> updateTrailById(@PathVariable("trailId") String trailId, @RequestBody TrailDTO trailDTO) throws TrailNotFoundException {
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Updating a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(trekBookingService.updateTrailById(trailDTO,trailId));
    }

    @DeleteMapping("/trails/{trailId}")
    public ResponseEntity<Trail> deleteTrailById(@PathVariable String trailId) throws TrailNotFoundException {
        Trail trail = trekBookingService.getTrailById(trailId);
        trekBookingService.deleteTrailById(trailId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "deleting a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(trail);
    }

    @PostMapping("/makebooking")
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValidException {
        Booking booking = trekBookingService.makeBooking(bookingDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Made a booking");
        return ResponseEntity.status(HttpStatus.CREATED).headers(header).body(booking);
    }

    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getAllBookingByCustomerName(@RequestParam String customerName) throws BookingNotFoundException {
        List<Booking> booking = trekBookingService.getAllBookingByCustomerName(customerName);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get all bookings by customer name");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(booking);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("bookingId") String bookingId) throws BookingNotFoundException {
        Booking booking = trekBookingService.getBookingById(bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(booking);
    }

    @PutMapping("booking/{bookingId}")
    public ResponseEntity<Booking> updateBookingById(@RequestBody BookingDTO bookingDTO, @PathVariable("bookingId") String bookingId) throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Booking booking = trekBookingService.updateBookingById(bookingDTO, bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Updated a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(booking);
    }

    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<Booking> deleteBookingById(@PathVariable String bookingId) throws BookingNotFoundException {
        Booking booking = trekBookingService.getBookingById(bookingId);
        trekBookingService.deleteBookingById(bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Deleted a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(booking);
    }

}






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

    public TrekBookingController(TrekBookingService mockTrekBookingService) {
        this.trekBookingService = mockTrekBookingService;
    }

    @GetMapping("/trails")
    public ResponseEntity<List<Trail>> getTrails() {
        List<Trail> retrievedTrails = trekBookingService.getAllTrails();
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get all trails");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(retrievedTrails);
    }

    @GetMapping("/trails/{trailId}")
    public ResponseEntity<Trail> getTrailById(@PathVariable String trailId) throws TrailNotFoundException {
        Trail retrievedTrail = trekBookingService.getTrailById(trailId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(retrievedTrail);
    }

    @PostMapping("/trails")
    public ResponseEntity<Trail> addNewTrail(@RequestBody TrailDTO trailDTO) {
        Trail addedTrail = trekBookingService.addNewTrail(trailDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Adding a new trail");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(addedTrail);
    }

    @PutMapping("trails/{trailId}")
    public ResponseEntity<Trail> updateTrailById(@PathVariable("trailId") String trailId, @RequestBody TrailDTO trailDTO) throws TrailNotFoundException {
        Trail updatedTrail = trekBookingService.updateTrailById(trailDTO, trailId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Updating a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(updatedTrail);
    }

    @DeleteMapping("/trails/{trailId}")
    public ResponseEntity<Trail> deleteTrailById(@PathVariable String trailId) throws TrailNotFoundException {
        Trail deletedTrail = trekBookingService.deleteTrailById(trailId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "deleting a trail by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(deletedTrail);
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValidException {
        Booking booking = trekBookingService.makeBooking(bookingDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Made a booking");
        return ResponseEntity.status(HttpStatus.CREATED).headers(header).body(booking);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookingByCustomerName(@RequestParam String customerName) throws BookingNotFoundException {
        List<Booking> retrievedBookings = trekBookingService.getAllBookingByCustomerName(customerName);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get all bookings by customer name");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(retrievedBookings);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("bookingId") String bookingId) throws BookingNotFoundException {
        Booking retrievedBooking = trekBookingService.getBookingById(bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Get a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(retrievedBooking);
    }

    @PutMapping("bookings/{bookingId}")
    public ResponseEntity<Booking> updateBookingById(@RequestBody BookingDTO bookingDTO, @PathVariable("bookingId") String bookingId) throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Booking updatedBooking = trekBookingService.updateBookingById(bookingDTO, bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Updated a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(updatedBooking);
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> deleteBookingById(@PathVariable String bookingId) throws BookingNotFoundException {
        Booking deletedBooking = trekBookingService.deleteBookingById(bookingId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Deleted a booking by Id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(deletedBooking);
    }

}






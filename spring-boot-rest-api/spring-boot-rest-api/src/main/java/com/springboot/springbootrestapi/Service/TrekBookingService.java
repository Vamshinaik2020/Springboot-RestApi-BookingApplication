package com.springboot.springbootrestapi.Service;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.Repository.BookingRepository;
import com.springboot.springbootrestapi.Repository.TrailRepository;
import com.springboot.springbootrestapi.exception.BookingNotFoundException;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValid;
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

    public Trail addNewTrail(Trail trail){
        Trail newtrail = trailRepository.save(trail);
        return trail;
    }

    public void deleteTrailById(String trailId) {
        trailRepository.deleteById(trailId);
    }

    public Trail updateTrailById(Trail trail, String trailId) throws TrailNotFoundException {
        Optional<Trail> updatedTrail = trailRepository.findById(trailId);
        if(updatedTrail.isEmpty()) {
            throw new TrailNotFoundException("Trail Not Found");
        }

        Trail trail1 = updatedTrail.get();
        trail1.setTitle(trail.getTitle());
        trail1.setStartAt(trail.getStartAt());
        trail1.setEndAt(trail.getEndAt());
        trail1.setMaximumAge(trail.getMaximumAge());
        trail1.setMinimumAge(trail.getMinimumAge());
        trail1.setUnitPrice(trail.getUnitPrice());
        return trailRepository.save(trail1);
    }

    public Booking makeBooking(BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValid {
        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());

        if (trail.isEmpty()) throw new TrailNotFoundException("Trail Not Found");

        Trail trail1 = trail.get();

        if (bookingDTO.getCustomerAge() >= trail1.getMinimumAge() && bookingDTO.getCustomerAge() <= trail1.getMaximumAge()) {
            Booking booking = new Booking(bookingDTO.getCustomerName(), bookingDTO.getCustomerAge(), bookingDTO.getGender(), bookingDTO.getTrailId());

            return bookingRepository.save(booking);
        }
        else throw new CustomerAgeNotValid("Customer Age Not valid");
    }

    public List<Booking> getAllBookingByCustomerName(String customerName) {
        return bookingRepository.getAllBookingByCustomerName(customerName);
    }

    public Optional<Booking> getBookingById(String bookingId){
        return bookingRepository.findById(bookingId);
    }

    public void deleteBookingById(String bookingId){
        bookingRepository.deleteById(bookingId);
    }

    public Booking updateBookingById(Booking booking, String bookingId) throws BookingNotFoundException {
        Optional<Booking> updatedBooking = bookingRepository.findById(bookingId);
        if(updatedBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking with Id Not Found");
        }
        Booking booking1 = updatedBooking.get();
        booking1.setCustomerName(booking.getCustomerName());
        booking1.setCustomerAge(booking.getCustomerAge());
        booking1.setGender(booking.getGender());
        booking1.setTrailId(booking.getTrailId());
        return bookingRepository.save(booking1);
    }

}





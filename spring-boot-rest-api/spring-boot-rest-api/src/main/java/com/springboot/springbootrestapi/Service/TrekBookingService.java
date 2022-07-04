package com.springboot.springbootrestapi.Service;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.DTO.TrailDTO;
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

    public Optional<Trail> getTrailById(TrailDTO trailDTO, String trailId) {
        return trailRepository.findById(trailId);
    }

    public Trail addNewTrail(TrailDTO trailDTO){
        Trail trail = new Trail(trailDTO.getTitle(), trailDTO.getStartAt(), trailDTO.getEndAt(), trailDTO.getMinimumAge(),trailDTO.getMaximumAge(), trailDTO.getUnitPrice());
        return trailRepository.save(trail);
    }

    public void deleteTrailById(TrailDTO trailDTO, String trailId) {
        trailRepository.deleteById(trailId);
    }

    public Trail updateTrailById(TrailDTO trailDTO, String trailId) throws TrailNotFoundException {
        if (trailRepository.findById(trailId).isPresent()) {

            Trail trail1 = trailRepository.findById(trailId).get();

            trail1.setTitle(trailDTO.getTitle());
            trail1.setStartAt(trailDTO.getStartAt());
            trail1.setEndAt(trailDTO.getEndAt());
            trail1.setMaximumAge(trailDTO.getMaximumAge());
            trail1.setMinimumAge(trailDTO.getMinimumAge());
            trail1.setUnitPrice(trailDTO.getUnitPrice());
            return trailRepository.save(trail1);
        }
        else throw new TrailNotFoundException("Trail Not Found");

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

    public List<Booking> getAllBookingByCustomerName(BookingDTO bookingDTO, String customerName) {
        return bookingRepository.getAllBookingByCustomerName(customerName);
    }

    public Optional<Booking> getBookingById(BookingDTO bookingDTO, String bookingId){
        return bookingRepository.findById(bookingId);
    }

    public void deleteBookingById(BookingDTO bookingDTO, String bookingId){
        bookingRepository.deleteById(bookingId);
    }

    public Booking updateBookingById(BookingDTO bookingDTO, String bookingId) throws BookingNotFoundException, CustomerAgeNotValid {
        Optional<Booking> updatedBooking = bookingRepository.findById(bookingId);
        if (updatedBooking.isEmpty()) throw new BookingNotFoundException("Booking with Id Not Found");

        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());
        Trail trail1 = trail.get();
        if (bookingDTO.getCustomerAge() >= trail1.getMinimumAge() && bookingDTO.getCustomerAge() <= trail1.getMaximumAge()) {
            Booking booking1 = updatedBooking.get();
            booking1.setCustomerName(bookingDTO.getCustomerName());
            booking1.setCustomerAge(bookingDTO.getCustomerAge());
            booking1.setGender(bookingDTO.getGender());
            booking1.setTrailId(bookingDTO.getTrailId());
            return bookingRepository.save(booking1);
        } else throw new CustomerAgeNotValid("Not valid");
    }

}





package com.springboot.springbootrestapi.Service;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.DTO.TrailDTO;
import com.springboot.springbootrestapi.Repository.BookingRepository;
import com.springboot.springbootrestapi.Repository.TrailRepository;
import com.springboot.springbootrestapi.exception.BookingNotFoundException;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValidException;
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

    public Trail getTrailById(String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if(trail.isEmpty()) throw new TrailNotFoundException("Trail with Id mentioned not found");
        return trail.get();
    }

    public Trail addNewTrail(TrailDTO trailDTO){
        Trail trail = new Trail(trailDTO.getName(), trailDTO.getStartAt(), trailDTO.getEndAt(), trailDTO.getMinimumAge(),trailDTO.getMaximumAge(), trailDTO.getUnitPrice());
        return trailRepository.save(trail);
    }

    public void deleteTrailById(String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if(trail.isEmpty()) throw new TrailNotFoundException("Trail with mentioned Id not found");
        trailRepository.deleteById(trailId);

    }

    public Trail updateTrailById(TrailDTO trailDTO, String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if (trail.isEmpty()) throw new TrailNotFoundException("Trail with mentioned Id Not Found");
        Trail trail1 = trail.get();

        trail1.setName(trailDTO.getName());
        trail1.setStartAt(trailDTO.getStartAt());
        trail1.setEndAt(trailDTO.getEndAt());
        trail1.setMaximumAge(trailDTO.getMaximumAge());
        trail1.setMinimumAge(trailDTO.getMinimumAge());
        trail1.setUnitPrice(trailDTO.getUnitPrice());
        return trailRepository.save(trail1);
    }

    public Booking makeBooking(BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValidException {
        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());
        if (trail.isEmpty()) throw new TrailNotFoundException("Trail Not Found");
        Trail trail1 = trail.get();

        if (bookingDTO.getCustomerAge() >= trail1.getMinimumAge() && bookingDTO.getCustomerAge() <= trail1.getMaximumAge()) {
            Booking booking = new Booking(bookingDTO.getCustomerName(), bookingDTO.getCustomerAge(), bookingDTO.getGender(), bookingDTO.getTrailId());
            return bookingRepository.save(booking);
        } throw new CustomerAgeNotValidException("Customer Age Not valid");
    }

    public List<Booking> getAllBookingByCustomerName(String customerName) throws BookingNotFoundException {
        List<Booking> booking = bookingRepository.getAllBookingByCustomerName(customerName);
        if(booking.isEmpty()) throw new BookingNotFoundException("Booking with the customer name not found");
        return booking;

    }

    public Booking getBookingById(String bookingId) throws BookingNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) throw new BookingNotFoundException("Booking with mentioned Id not found");
        return booking.get();

    }

    public void deleteBookingById(String bookingId) throws BookingNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty()) throw new BookingNotFoundException("Booking with mentioned Id not found");
        bookingRepository.deleteById(bookingId);
    }

    public Booking updateBookingById(BookingDTO bookingDTO, String bookingId) throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) throw new BookingNotFoundException("Booking with mentioned Id not found");
        Booking booking1 = booking.get();

        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());
        if(trail.isEmpty()) throw new TrailNotFoundException("Trail with mentioned Id not found");
        Trail trail1 = trail.get();

        if (bookingDTO.getCustomerAge() >= trail1.getMinimumAge() && bookingDTO.getCustomerAge() <= trail1.getMaximumAge()) {
            booking1.setCustomerName(bookingDTO.getCustomerName());
            booking1.setCustomerAge(bookingDTO.getCustomerAge());
            booking1.setGender(bookingDTO.getGender());
            return bookingRepository.save(booking1);
        } throw new CustomerAgeNotValidException("Customer Age is not valid");
    }
}





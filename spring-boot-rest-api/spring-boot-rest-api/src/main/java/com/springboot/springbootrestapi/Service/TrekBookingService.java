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


    public Trail getTrailById(String trailId) {
        for( Trail trail : trailRepository.findAll()){
            if(trail.getId().equals(trailId))
                return trail;
        }
        return null;
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


    public Booking getBookingByCustomerName(BookingDTO bookingDTO) throws TrailNotFoundException, BookingIdNotFoundException {
        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());


        if(trail.isEmpty()) throw new TrailNotFoundException("Trail Not Found");
        Trail trail1 = trail.get();

        for(Booking booking : bookingRepository.findAll()){
            if(bookingDTO.getCustomerName().equals(booking.getCustomerName()))
                return booking;
        }
        return null;
    }



    public List<Booking> getAllBookings(BookingDTO bookingDTO){
        Trail trail = getTrailById(bookingDTO.getTrailId());

        if(trail == null){
            return null;
        }
        return bookingRepository.findAll();
    }




}

/* public List<Trail> getAllTrails() {
        return trailRepository.findAll();
    }


    public Booking makeBooking(String trailId, Booking booking) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);

        if (trail.isEmpty()) throw new TrailNotFoundException(trail, trailId);
        else return booking;
    }


    public Booking getBookingById(String bookingId, String id) throws BookingIdNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingIdNotFoundException(bookingId));
    }   */




    //public Booking makeBooking(BookingDTO bookingDTO) throws TrailNotFoundException {
    //Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());

    //Trail trailWeWereLookingFor = trail.get();

    //Booking booking = new Booking();

    //return booking;
    // }


    //public Booking makeBooking(String trailId, Booking booking) {
    //}


    //public Booking createBooking(Booking booking) {
     //   Optional<Trail> trail = trailRepository.findById(booking.getTrailId());
       // Booking newbooking = new Booking();
        //BeanUtils.copyProperties(booking, newbooking);
        //newbooking.setTrail(trail.get());
        //return bookingRepository.save(newbooking);






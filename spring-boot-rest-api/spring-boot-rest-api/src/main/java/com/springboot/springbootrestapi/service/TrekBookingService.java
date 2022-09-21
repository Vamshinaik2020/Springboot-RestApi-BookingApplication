package com.springboot.springbootrestapi.service;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.DTO.FavouriteDTO;
import com.springboot.springbootrestapi.DTO.TrailDTO;
import com.springboot.springbootrestapi.exception.*;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Favourite;
import com.springboot.springbootrestapi.model.Trail;
import com.springboot.springbootrestapi.repository.BookingRepository;
import com.springboot.springbootrestapi.repository.FavouriteRepository;
import com.springboot.springbootrestapi.repository.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrekBookingService {
    private TrailRepository trailRepository;
    private BookingRepository bookingRepository;
    @Autowired
    private FavouriteRepository favouriteRepository;

    public TrekBookingService(@Autowired TrailRepository mockTrailRepository, @Autowired BookingRepository mockBookingRepository) {
        this.trailRepository = mockTrailRepository;
        this.bookingRepository = mockBookingRepository;
    }

    public List<Trail> getAllTrails() {
        return trailRepository.findAll();
    }

    public Trail getTrailById(String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if (trail.isEmpty()) {
            throw new TrailNotFoundException("Trail with Id mentioned not found");
        }
        return trail.get();
    }

    public Trail addNewTrail(TrailDTO trailDTO) {
        Trail trail = new Trail(trailDTO.getName(), trailDTO.getStartAt(), trailDTO.getEndAt(), trailDTO.getMinimumAge(), trailDTO.getMaximumAge(), trailDTO.getUnitPrice());
        return trailRepository.save(trail);
    }

    public List<Trail> getTrailByName(String name) {
        List<Trail> retrievedTrail = trailRepository.findTrailByName(name);
        if (retrievedTrail.isEmpty()) {
            throw new TrailNotFoundException("Trail with mentioned name not found");
        }
        return retrievedTrail;
    }

    public Trail deleteTrailById(String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if (trail.isEmpty()) {
            throw new TrailNotFoundException("Trail with mentioned Id not found");
        }
        trailRepository.deleteById(trailId);
        return trail.get();
    }

    public Trail updateTrailById(TrailDTO trailDTO, String trailId) throws TrailNotFoundException {
        Optional<Trail> trail = trailRepository.findById(trailId);
        if (trail.isEmpty()) {
            throw new TrailNotFoundException("Trail with mentioned Id Not Found");
        }
        Trail retrievedTrail = trail.get();

        retrievedTrail.setName(trailDTO.getName());
        retrievedTrail.setStartAt(trailDTO.getStartAt());
        retrievedTrail.setEndAt(trailDTO.getEndAt());
        retrievedTrail.setMaximumAge(trailDTO.getMaximumAge());
        retrievedTrail.setMinimumAge(trailDTO.getMinimumAge());
        retrievedTrail.setUnitPrice(trailDTO.getUnitPrice());
        return trailRepository.save(retrievedTrail);
    }

    public Booking makeBooking(BookingDTO bookingDTO) throws TrailNotFoundException, CustomerAgeNotValidException {
        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());
        if (trail.isEmpty()) {
            throw new TrailNotFoundException("Trail Not Found");
        }
        Trail retrievedTrail = trail.get();

        if (validation(bookingDTO, retrievedTrail)) {
            Booking booking = new Booking(bookingDTO.getCustomerName(), bookingDTO.getCustomerAge(), bookingDTO.getGender(), bookingDTO.getTrailId());
            return bookingRepository.save(booking);
        }
        throw new CustomerAgeNotValidException("Customer Age Not valid");
    }

    public List<Booking> getAListOfBookingsByCustomerName(String customerName) throws BookingNotFoundException {
        List<Booking> retrievedBookings = bookingRepository.findBookingsByCustomerName(customerName);
        if (retrievedBookings.isEmpty()) {
            throw new BookingNotFoundException("Booking with the customer name not found");
        }
        return retrievedBookings;
    }

    public Booking getBookingById(String bookingId) throws BookingNotFoundException {
        Optional<Booking> retrievedBooking = bookingRepository.findById(bookingId);
        if (retrievedBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking with mentioned Id not found");
        }
        return retrievedBooking.get();
    }

    public Booking deleteBookingById(String bookingId) throws BookingNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new BookingNotFoundException("Booking with mentioned Id not found");
        }
        bookingRepository.deleteById(bookingId);
        return booking.get();
    }

    public Booking updateBookingById(BookingDTO bookingDTO, String bookingId) throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new BookingNotFoundException("Booking with mentioned Id not found");
        }
        Booking retrievedBooking = booking.get();

        Optional<Trail> trail = trailRepository.findById(bookingDTO.getTrailId());
        if (trail.isEmpty()) {
            throw new TrailNotFoundException("Trail with mentioned Id not found");
        }
        Trail retrievedTrail = trail.get();

        if (validation(bookingDTO, retrievedTrail)) {
            retrievedBooking.setCustomerName(bookingDTO.getCustomerName());
            retrievedBooking.setCustomerAge(bookingDTO.getCustomerAge());
            retrievedBooking.setGender(bookingDTO.getGender());
            retrievedBooking.setTrailId(bookingDTO.getTrailId());
            return bookingRepository.save(retrievedBooking);
        }
        throw new CustomerAgeNotValidException("Customer Age is not valid");
    }

    private boolean validation(BookingDTO bookingDTO, Trail trail) {
        return bookingDTO.getCustomerAge() >= trail.getMinimumAge() && bookingDTO.getCustomerAge() <= trail.getMaximumAge();
    }

    public Favourite addToFavourites(FavouriteDTO favouriteDTO) {
        Optional<Favourite> favTrail = favouriteRepository.findByTrailId(favouriteDTO.getTrailId());
        if (favTrail.isPresent()) {
            throw new FavouriteAlreadyPresentException("Already added to Favourites");
        }
        Favourite favourite = new Favourite(favouriteDTO.getTrailId(), favouriteDTO.getCustomerName());
        return favouriteRepository.save(favourite);
    }

    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

    public Favourite getFavouriteById(String favouriteId) {
        Optional<Favourite> favourite = favouriteRepository.findById(favouriteId);
        if (favourite.isEmpty()) {
            throw new FavouriteNotFound("Favourite with mentioned Id not found");
        }
        return favourite.get();
    }

    public Favourite removeTrailFromFavourite(String favouriteId) {
        Optional<Favourite> deleteFavourite = favouriteRepository.findById(favouriteId);
        if (deleteFavourite.isEmpty()) {
            throw new FavouriteNotFound("Favourite Trail with mentioned Id not found");
        }
        favouriteRepository.deleteById(favouriteId);
        return deleteFavourite.get();
    }
}






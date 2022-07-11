package com.springboot.springbootrestapi;

import com.springboot.springbootrestapi.DTO.BookingDTO;
import com.springboot.springbootrestapi.DTO.TrailDTO;
import com.springboot.springbootrestapi.Repository.BookingRepository;
import com.springboot.springbootrestapi.Repository.TrailRepository;
import com.springboot.springbootrestapi.Service.TrekBookingService;
import com.springboot.springbootrestapi.exception.BookingNotFoundException;
import com.springboot.springbootrestapi.exception.CustomerAgeNotValidException;
import com.springboot.springbootrestapi.exception.TrailNotFoundException;
import com.springboot.springbootrestapi.model.Booking;
import com.springboot.springbootrestapi.model.Trail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrekBookingServiceTests {
    @Mock
    private TrailRepository mockTrailRepository;
    @Mock
    private BookingRepository mockBookingRepository;
    private TrekBookingService trekBookingService;

    @BeforeEach
    public void init() {
        this.trekBookingService = new TrekBookingService(mockTrailRepository, mockBookingRepository);
    }

    @Test
    public void getAllTrailsTest() {
        List<Trail> trails = new ArrayList<>();
        Trail trail1 = new Trail("Manali", "12:00", "14:00", 9, 50, 8990);
        Trail trail2 = new Trail("Udaipur", "12:00", "14:00", 9, 50, 8990);
        Trail trail3 = new Trail("Shimla", "12:00", "14:00", 9, 50, 8990);
        trails.add(trail1);
        trails.add(trail2);
        trails.add(trail3);

        when(mockTrailRepository.findAll()).thenReturn(trails);

        List<Trail> list = trekBookingService.getAllTrails();

        Assertions.assertEquals(trails, list);
        verify(mockTrailRepository).findAll();
    }

    @Test
    public void getTrailByIdTest() throws TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);

        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        Trail returnedTrail = trekBookingService.getTrailById("abc");

        Assertions.assertEquals(trail, returnedTrail);
        verify(mockTrailRepository).findById("abc");
    }


    @Test
    public void addNewTrailTest() {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);

        when(mockTrailRepository.save(trail)).thenReturn(trail);

        TrailDTO requiredTrail = new TrailDTO("Warangal", "12:00", "15:00", 8, 70, 5990);
        Trail savedTrail = trekBookingService.addNewTrail(requiredTrail);

        Assertions.assertEquals(trail, savedTrail);  //we’re checking that our service answered our call with the correct expected value
        verify(mockTrailRepository).save(trail);   //we’re checking that our repository was called
    }

    @Test
    public void deleteTrailByIdTest() throws TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);

        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        trekBookingService.deleteTrailById("abc");

        verify(mockTrailRepository).deleteById("abc");
    }

    @Test
    public void updateTrailByIdTest() throws TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        TrailDTO updatedTrail = new TrailDTO("Hanamkonda", "12:00", "15:00", 8, 70, 5990);
        trekBookingService.updateTrailById(updatedTrail, "abc");

        verify(mockTrailRepository).save(trail);
        verify(mockTrailRepository).findById("abc");
    }

    @Test
    public void makeBooking() throws TrailNotFoundException, CustomerAgeNotValidException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        Booking booking = new Booking("Mahesh", 30, "Male", "abc");
        when(mockBookingRepository.save(booking)).thenReturn(booking);

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 30, "Male", "abc");
        Booking createdBooking = trekBookingService.makeBooking(returnedBookingDTO);

        Assertions.assertEquals(booking, createdBooking);
        verify(mockBookingRepository).save(booking);
    }

    @Test
    public void getAllBookingsByCustomerNameTest() throws BookingNotFoundException {
        List<Booking> bookings = new ArrayList<>();
        Booking booking1 = new Booking("Mahesh", 30, "Male", "abc");
        Booking booking2 = new Booking("Mahesh", 30, "Male", "bcd");
        bookings.add(booking1);
        bookings.add(booking2);

        when(mockBookingRepository.getAllBookingByCustomerName("Mahesh")).thenReturn((List<Booking>) bookings);

        List<Booking> returnedBooking = trekBookingService.getAllBookingByCustomerName("Mahesh");

        Assertions.assertEquals(bookings, returnedBooking);
    }

    @Test
    public void getBookingByIdTest() throws BookingNotFoundException {
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");

        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));

        Booking returnedBooking = trekBookingService.getBookingById("123");

        Assertions.assertEquals(booking, returnedBooking);
        verify(mockBookingRepository).findById("123");
    }

    @Test
    public void deleteBookingByIdTest() throws BookingNotFoundException {
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");

        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));

        trekBookingService.deleteBookingById("123");

        verify(mockBookingRepository).deleteById("123");
    }

    @Test
    public void updateBookingByIdTest() throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");

        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 40, "Male", "abc");
        trekBookingService.updateBookingById(returnedBookingDTO, "123");

        verify(mockBookingRepository).save(booking);
        verify(mockBookingRepository).findById("123");
    }
}

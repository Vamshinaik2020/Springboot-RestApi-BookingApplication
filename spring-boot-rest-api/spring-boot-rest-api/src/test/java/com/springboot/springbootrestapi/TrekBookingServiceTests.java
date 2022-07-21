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
    public void shouldReturnAllTrails() {
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
    public void shouldReturnATrail_GivenTrailId() throws TrailNotFoundException {
        Trail expectedTrail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(expectedTrail));

        Trail actualTrail = trekBookingService.getTrailById("abc");

        Assertions.assertEquals(expectedTrail, actualTrail);
        verify(mockTrailRepository).findById("abc");
    }

    @Test
    public void shouldReturnTrailNotFound_GivenTrailId_ToFindATrail() {
        TrailNotFoundException expectedException = new TrailNotFoundException("Trail with mentioned Id not found");
        when(mockTrailRepository.findById("abc")).thenThrow(expectedException);

        Exception exception = Assertions.assertThrows(TrailNotFoundException.class, () -> trekBookingService.getTrailById("abc"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Trail with mentioned Id not found"));
        /*
         The first argument is the expected type of exception to be thrown. The second argument is a function that will throw that exception
         The assertThrows method executes the function and validates that the expected type of exception is thrown.
        */
    }

    @Test
    public void shouldAddNewTrail() {
        Trail expectedTrail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.save(expectedTrail)).thenReturn(expectedTrail);

        TrailDTO requiredTrail = new TrailDTO("Warangal", "12:00", "15:00", 8, 70, 5990);
        Trail actualTrail = trekBookingService.addNewTrail(requiredTrail);

        Assertions.assertEquals(expectedTrail, actualTrail);  //we’re checking that our service answered our call with the correct expected value
        verify(mockTrailRepository).save(expectedTrail);   //we’re checking that our repository was called
    }

    @Test
    public void shouldDeleteATrail_GivenTrailId() throws TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        trekBookingService.deleteTrailById("abc");

        verify(mockTrailRepository).deleteById("abc");
    }

    @Test
    public void shouldReturnTrailNotFound_GivenTrailId_ToDeleteATrail() {
        TrailNotFoundException expectedException = new TrailNotFoundException("Trail with mentioned Id not found");
        when(mockTrailRepository.findById("abc")).thenThrow(expectedException);

        Exception exception = Assertions.assertThrows(TrailNotFoundException.class, () -> trekBookingService.deleteTrailById("abc"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Trail with mentioned Id not found"));
    }

    @Test
    public void shouldUpdateATrail_GivenTrailId() throws TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        TrailDTO updatedTrailDTO = new TrailDTO("Hanamkonda", "12:00", "15:00", 8, 70, 5990);
        trekBookingService.updateTrailById(updatedTrailDTO, "abc");

        verify(mockTrailRepository).save(trail);
        verify(mockTrailRepository).findById("abc");
    }

    @Test
    public void shouldReturnTrailNotFound_GivenTrailId_ToUpdateATrail() {
        TrailDTO updatedTrailDTO = new TrailDTO("Hanamkonda", "12:00", "15:00", 8, 70, 5990);

        TrailNotFoundException expectedException = new TrailNotFoundException("Trail with mentioned Id not found");
        when(mockTrailRepository.findById("abc")).thenThrow(expectedException);

        Exception exception = Assertions.assertThrows(TrailNotFoundException.class, () -> trekBookingService.updateTrailById(updatedTrailDTO, "abc"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Trail with mentioned Id not found"));
    }

    @Test
    public void shouldMakeABooking() throws TrailNotFoundException, CustomerAgeNotValidException {
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
    public void shouldReturnTrailNotFound_ToMakeABooking()  {
        TrailNotFoundException expectedException = new TrailNotFoundException("Trail with mentioned Id not found");
        when(mockTrailRepository.findById("abc")).thenThrow(expectedException);

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 10, "Male", "abc");
        Exception exception = Assertions.assertThrows(TrailNotFoundException.class,()-> trekBookingService.makeBooking(returnedBookingDTO));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Trail with mentioned Id not found"));
    }

    @Test
    public void shouldReturnCustomerAgeNotValid_ToMakeABooking() {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 5, "Male", "abc");

        Exception exception = Assertions.assertThrows(CustomerAgeNotValidException.class, ()-> trekBookingService.makeBooking(returnedBookingDTO));
    }

    @Test
    public void shouldReturnAllBookings_GivenCustomerName() throws BookingNotFoundException {
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
    public void shouldReturnBookingNotFound_GivenCustomerName() {
        BookingNotFoundException expectedException = new BookingNotFoundException("Booking with the customer name not found");
        when(mockBookingRepository.getAllBookingByCustomerName("Mahesh")).thenThrow(expectedException);

        Exception exception=  Assertions.assertThrows(BookingNotFoundException.class, () -> trekBookingService.getAllBookingByCustomerName("Mahesh"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Booking with the customer name not found"));
    }

    @Test
    public void shouldReturnBooking_GivenBookingId() throws BookingNotFoundException {
        Booking expectedBooking = new Booking("Mahesh", 30, "Male", "abc");
        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(expectedBooking));

        Booking actualBooking = trekBookingService.getBookingById("123");

        Assertions.assertEquals(expectedBooking, actualBooking);
        verify(mockBookingRepository).findById("123");
    }

    @Test
    public void shouldReturnBookingNotFound_GivenBookingId_ToFindABooking() {
        BookingNotFoundException expectedException = new BookingNotFoundException("Booking with mentioned Id not found");
        when(mockBookingRepository.findById("123")).thenThrow(expectedException);

        Exception exception = Assertions.assertThrows(BookingNotFoundException.class, ()-> trekBookingService.getBookingById("123"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Booking with mentioned Id not found"));
    }

    @Test
    public void shouldDeleteABooking_GivenBookingId() throws BookingNotFoundException {
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");
        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));

        trekBookingService.deleteBookingById("123");

        verify(mockBookingRepository).deleteById("123");
    }

    @Test
    public void shouldReturnBookingNotFound_GivenBookingId_ToDeleteABooking() {
        BookingNotFoundException expectedException = new BookingNotFoundException("Booking with mentioned Id not found");
        when(mockBookingRepository.findById("123")).thenThrow(expectedException);

        Exception exception = Assertions.assertThrows(BookingNotFoundException.class, ()-> trekBookingService.deleteBookingById("123"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Booking with mentioned Id not found"));
    }

    @Test
    public void shouldUpdateABooking_GivenBookingId() throws BookingNotFoundException, CustomerAgeNotValidException, TrailNotFoundException {
        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");

        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        BookingDTO updatedBookingDTO = new BookingDTO("Mahesh", 40, "Male", "abc");
        trekBookingService.updateBookingById(updatedBookingDTO, "123");

        verify(mockBookingRepository).save(booking);
        verify(mockBookingRepository).findById("123");
    }
    @Test
    public void shouldReturnBookingNotFound_ToUpdateABooking() {
        BookingNotFoundException expectedException = new BookingNotFoundException("Booking with mentioned Id not found");
        when(mockBookingRepository.findById("123")).thenThrow(expectedException);

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 10, "Male", "abc");
        Exception exception = Assertions.assertThrows(BookingNotFoundException.class, () -> trekBookingService.updateBookingById(returnedBookingDTO, "123"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Booking with mentioned Id not found"));
    }

    @Test
    public void shouldReturnTrailNotFound_ToUpdateABooking() {
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");
        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));

        TrailNotFoundException expectedException = new TrailNotFoundException("Trail with mentioned Id not found");
        when(mockTrailRepository.findById("abc")).thenThrow(expectedException);

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 40, "Male", "abc");

        Assertions.assertThrows(TrailNotFoundException.class, ()-> trekBookingService.updateBookingById(returnedBookingDTO, "123"));
    }
    @Test
    public void shouldReturnCustomerAgeNotValid_ToUpdateBooking() {
        Booking booking = new Booking("Mahesh", 30, "Male", "abc");
        when(mockBookingRepository.findById("123")).thenReturn(Optional.of(booking));

        Trail trail = new Trail("Warangal", "12:00", "15:00", 8, 70, 5990);
        when(mockTrailRepository.findById("abc")).thenReturn(Optional.of(trail));

        BookingDTO returnedBookingDTO = new BookingDTO("Mahesh", 5, "Male", "abc");
        Exception exception = Assertions.assertThrows(CustomerAgeNotValidException.class, ()-> trekBookingService.updateBookingById(returnedBookingDTO, "123"));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Customer Age is not valid"));
    }
}


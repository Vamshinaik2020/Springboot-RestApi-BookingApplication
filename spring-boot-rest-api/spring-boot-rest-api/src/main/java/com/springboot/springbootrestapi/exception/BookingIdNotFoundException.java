package com.springboot.springbootrestapi.exception;

import com.springboot.springbootrestapi.model.Booking;

public class BookingIdNotFoundException extends Throwable {
    public BookingIdNotFoundException(String booking) {
    }

    public BookingIdNotFoundException(Booking booking) {

    }
}

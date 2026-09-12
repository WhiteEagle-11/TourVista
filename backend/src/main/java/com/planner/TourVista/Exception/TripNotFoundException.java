package com.planner.TourVista.Exception;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(String message) {
        super(message);
    }
}
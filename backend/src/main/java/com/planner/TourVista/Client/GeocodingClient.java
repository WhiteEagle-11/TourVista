package com.planner.TourVista.Client;

import com.planner.TourVista.dto.GeoCoordinates;

public interface GeocodingClient {

    GeoCoordinates geocode(String destination);
}
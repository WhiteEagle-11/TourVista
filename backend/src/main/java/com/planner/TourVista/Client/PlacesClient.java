package com.planner.TourVista.Client;

import java.util.List;

import com.planner.TourVista.dto.PlaceResponse;

public interface PlacesClient {

    List<PlaceResponse> searchPlaces(String destination);
}
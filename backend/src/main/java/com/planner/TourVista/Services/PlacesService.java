package com.planner.TourVista.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.TourVista.Client.PlacesClient;
import com.planner.TourVista.dto.PlaceResponse;

@Service
public class PlacesService {

    private final PlacesClient placesClient;

    public PlacesService(PlacesClient placesClient) {
        this.placesClient = placesClient;
    }

    public List<PlaceResponse> searchPlaces(String destination) {
        return placesClient.searchPlaces(destination);
    }
}
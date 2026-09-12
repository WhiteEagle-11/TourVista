package com.planner.TourVista.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.PlacesService;
import com.planner.TourVista.dto.PlaceResponse;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

    private final PlacesService placesService;

    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> searchPlaces(
            @RequestParam String destination) {

        return ResponseEntity.ok(
                placesService.searchPlaces(destination)
        );
    }
}
package com.planner.TourVista.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.TripService;
import com.planner.TourVista.dto.CreateTripRequest;
import com.planner.TourVista.dto.TripResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllTrips(){
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
public ResponseEntity<TripResponse> getTripById(@PathVariable Long id) {
    return ResponseEntity.ok(tripService.getTripById(id));
}


     @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody CreateTripRequest request) {
        TripResponse response = tripService.createTrip(request);
        return ResponseEntity.ok(response);
    }


@PutMapping("/{id}")
public ResponseEntity<TripResponse> updateTrip(
        @PathVariable Long id,
        @Valid @RequestBody CreateTripRequest request) {

    TripResponse response = tripService.updateTrip(id, request);

    return ResponseEntity.ok(response);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {

    tripService.deleteTrip(id);

    return ResponseEntity.noContent().build();
}
}

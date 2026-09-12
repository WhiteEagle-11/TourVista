package com.planner.TourVista.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.ItineraryService;
import com.planner.TourVista.dto.ItineraryResponse;
import com.planner.TourVista.dto.Recommendation;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ItineraryResponse> optimize(
            @RequestParam int tripDays,
            @RequestBody List<Recommendation> recommendations) {

        return ResponseEntity.ok(
                itineraryService.optimize(
                        recommendations,
                        tripDays
                )
        );
    }
}
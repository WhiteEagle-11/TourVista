package com.planner.TourVista.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.TravelPlanService;
import com.planner.TourVista.dto.RecommendationRequest;
import com.planner.TourVista.dto.TravelPlanResponse;

@RestController
@RequestMapping("/api/travel-plans")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService) {
        this.travelPlanService = travelPlanService;
    }

    @PostMapping
    public ResponseEntity<TravelPlanResponse> generatePlan(
            @RequestBody RecommendationRequest request) {

        return ResponseEntity.ok(
                travelPlanService.generatePlan(request)
        );
    }
}
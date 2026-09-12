package com.planner.TourVista.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.RecommendationService;
import com.planner.TourVista.dto.Recommendation;
import com.planner.TourVista.dto.RecommendationRequest;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<List<Recommendation>> recommend(
            @RequestBody RecommendationRequest request) {

        return ResponseEntity.ok(
                recommendationService.recommend(request)
        );
    }
}
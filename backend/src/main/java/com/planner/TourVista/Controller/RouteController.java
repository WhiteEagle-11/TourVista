package com.planner.TourVista.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.RouteService;
import com.planner.TourVista.dto.RouteResponse;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<RouteResponse> getRoute(
            @RequestParam double startLatitude,
            @RequestParam double startLongitude,
            @RequestParam double endLatitude,
            @RequestParam double endLongitude) {

        return ResponseEntity.ok(
                routeService.getRoute(
                        startLatitude,
                        startLongitude,
                        endLatitude,
                        endLongitude
                )
        );
    }
}
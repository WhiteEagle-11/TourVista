package com.planner.TourVista.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.TourVista.dto.ItineraryDay;
import com.planner.TourVista.dto.ItineraryResponse;
import com.planner.TourVista.dto.Recommendation;

@Service
public class ItineraryService {

    public ItineraryResponse optimize(
            List<Recommendation> recommendations,
            int tripDays) {

        if (recommendations == null
                || recommendations.isEmpty()
                || tripDays <= 0) {

            return new ItineraryResponse(List.of());
        }

        int placesPerDay =
                (int) Math.ceil(
                        (double) recommendations.size() / tripDays
                );

        List<ItineraryDay> days = new ArrayList<>();

        for (int day = 0; day < tripDays; day++) {

            int start = day * placesPerDay;

            if (start >= recommendations.size()) {
                break;
            }

            int end = Math.min(
                    start + placesPerDay,
                    recommendations.size()
            );

            List<Recommendation> dayRecommendations =
                    new ArrayList<>(
                            recommendations.subList(start, end)
                    );

            days.add(
                    new ItineraryDay(
                            day + 1,
                            dayRecommendations
                    )
            );
        }

        return new ItineraryResponse(days);
    }
}
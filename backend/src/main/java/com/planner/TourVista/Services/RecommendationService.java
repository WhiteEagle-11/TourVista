package com.planner.TourVista.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.planner.TourVista.dto.PlaceResponse;
import com.planner.TourVista.dto.Recommendation;
import com.planner.TourVista.dto.RecommendationRequest;
import com.planner.TourVista.dto.WeatherResponse;

@Service
public class RecommendationService {

    public List<Recommendation> recommend(RecommendationRequest request) {

        if (request.getPlaces() == null || request.getPlaces().isEmpty()) {
            return List.of();
        }

        List<String> interests = request.getInterests() == null
                ? List.of()
                : request.getInterests();

        List<Recommendation> recommendations = new ArrayList<>();

        for (PlaceResponse place : request.getPlaces()) {

            double interestScore = calculateInterestScore(place, interests);
double qualityScore = calculateQualityScore(place);
double weatherScore = calculateWeatherScore(request.getWeather());

double travelScore = calculateTravelScore(
        place,
        request.getDestinationLatitude(),
        request.getDestinationLongitude()
);

double budgetScore = calculateBudgetScore(
        request.getBudget(),
        request.getDays()
);

double durationScore = calculateDurationScore(request.getDays());

double score = interestScore
        + qualityScore
        + weatherScore
        + travelScore
        + budgetScore
        + durationScore;

            String reason = buildReason(
            interestScore,
            qualityScore,
            weatherScore
);

            recommendations.add(
                    new Recommendation(place, score, reason)
            );
        }

        recommendations.sort(
                Comparator.comparingDouble(Recommendation::getScore)
                        .reversed()
        );

        return recommendations;
    }

    private static final Map<String, List<String>> INTEREST_CATEGORY_MAP = Map.of(
        "history", List.of(
                "history",
                "historic",
                "memorial",
                "ruins",
                "ruines",
                "archaeology",
                "castle",
                "monument"
        ),
        "art", List.of(
                "art",
                "museum",
                "gallery",
                "culture"
        ),
        "food", List.of(
                "food",
                "restaurant",
                "cafe",
                "cuisine"
        ),
        "nature", List.of(
                "nature",
                "park",
                "garden",
                "hiking"
        ),
        "shopping", List.of(
                "shopping",
                "mall",
                "market"
        ),
        "nightlife", List.of(
                "nightlife",
                "nightclub",
                "bar",
                "entertainment"
        ),
        "architecture", List.of(
                "architecture",
                "monument",
                "building",
                "landmark"
        )
);

   private double calculateInterestScore(
        PlaceResponse place,
        List<String> interests) {

    if (interests.isEmpty()
            || place.getCategories() == null
            || place.getCategories().isEmpty()) {

        return 0;
    }

    double pointsPerInterest = 35.0 / interests.size();
    double score = 0;

    for (String interest : interests) {

        if (interest == null || interest.isBlank()) {
            continue;
        }

        String normalizedInterest = interest
                .toLowerCase()
                .trim();

        List<String> matchingKeywords =
                INTEREST_CATEGORY_MAP.getOrDefault(
                        normalizedInterest,
                        List.of(normalizedInterest)
                );

        boolean matches = place.getCategories()
                .stream()
                .filter(category -> category != null)
                .map(category -> category.toLowerCase())
                .anyMatch(category ->
                        matchingKeywords.stream()
                                .anyMatch(category::contains)
                );

        if (matches) {
            score += pointsPerInterest;
        }
    }

    return Math.min(score, 35);
}





    private String buildReason(
        double interestScore,
        double qualityScore,
        double weatherScore) {

    if (interestScore > 0 && qualityScore > 0 && weatherScore >= 10) {
        return "Matches your interests, is a strong attraction, and has suitable weather.";
    }

    if (interestScore > 0 && qualityScore > 0) {
        return "Matches your interests and is a strong attraction.";
    }

    if (interestScore > 0) {
        return "Matches your interests.";
    }

    if (qualityScore > 0) {
        return "Recommended as a strong attraction.";
    }

    return "Limited matching information available.";
}





    private double calculateQualityScore(PlaceResponse place) {

    if (place.getCategories() == null
            || place.getCategories().isEmpty()) {
        return 0;
    }

    double score = 0;

    for (String category : place.getCategories()) {

        if (category == null || category.isBlank()) {
            continue;
        }

        String normalized = category.toLowerCase().trim();

        if (normalized.matches("tourism\\.sights\\..+")) {
            score = Math.max(score, 20);
        } else if (normalized.equals("tourism.sights")) {
            score = Math.max(score, 15);
        } else if (normalized.equals("tourism")) {
            score = Math.max(score, 5);
        }
    }

    return score;
}

private double calculateWeatherScore(WeatherResponse weather) {

    if (weather == null) {
        return 0;
    }

    double temperature = weather.getTemperature();
    double windSpeed = weather.getWindSpeed();

    // Severe weather conditions
    if (temperature < 5 || temperature > 38 || windSpeed > 40) {
        return 0;
    }

    // Poor conditions
    if (temperature < 10 || temperature > 33 || windSpeed > 30) {
        return 5;
    }

    // Moderate conditions
    if (temperature < 15 || temperature > 28 || windSpeed > 20) {
        return 10;
    }

    // Good sightseeing conditions
    return 15;
}


private double calculateTravelScore(
        PlaceResponse place,
        double destinationLatitude,
        double destinationLongitude) {

    double distance = calculateDistance(
            destinationLatitude,
            destinationLongitude,
            place.getLatitude(),
            place.getLongitude()
    );

    if (distance <= 2) {
        return 15;
    }

    if (distance <= 5) {
        return 10;
    }

    if (distance <= 10) {
        return 5;
    }

    return 0;
}


private double calculateDistance(
        double lat1,
        double lon1,
        double lat2,
        double lon2) {

    final double EARTH_RADIUS_KM = 6371.0;

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1))
            * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2)
            * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c;
}


private double calculateBudgetScore(double budget, int days) {

    if (budget <= 0 || days <= 0) {
        return 0;
    }

    double dailyBudget = budget / days;

    if (dailyBudget >= 200) {
        return 10;
    }

    if (dailyBudget >= 100) {
        return 7;
    }

    if (dailyBudget >= 50) {
        return 4;
    }

    return 0;
}


private double calculateDurationScore(int days) {

    if (days <= 0) {
        return 0;
    }

    if (days == 1) {
        return 2;
    }

    if (days <= 3) {
        return 3;
    }

    if (days <= 6) {
        return 5;
    }

    return 4;
}

}
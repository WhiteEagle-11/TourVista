package com.planner.TourVista.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.TourVista.Entity.Trip;
import com.planner.TourVista.Exception.TripNotFoundException;
import com.planner.TourVista.Repository.TripRepository;
import com.planner.TourVista.dto.CreateTripRequest;
import com.planner.TourVista.dto.TripResponse;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }

public TripResponse createTrip(CreateTripRequest request) {
    Trip trip = new Trip();
    trip.setDestination(request.getDestination());
    trip.setDays(request.getDays());
    trip.setBudget(request.getBudget());
    trip.setInterests(request.getInterests());

    Trip savedTrip = tripRepository.save(trip);

    return new TripResponse(
            savedTrip.getId(),
            savedTrip.getDestination(),
            savedTrip.getDays(),
            savedTrip.getBudget(),
            savedTrip.getInterests()
    );
}
    public List<TripResponse> getAllTrips() {
    return tripRepository.findAll()
            .stream()
            .map(trip -> new TripResponse(
                    trip.getId(),
                    trip.getDestination(),
                    trip.getDays(),
                    trip.getBudget(),
                    trip.getInterests()
            ))
            .toList();
}

public TripResponse getTripById(Long id) {
    Trip trip = tripRepository.findById(id)
            .orElseThrow(() -> new TripNotFoundException("Trip not found"));

    return new TripResponse(
            trip.getId(),
            trip.getDestination(),
            trip.getDays(),
            trip.getBudget(),
            trip.getInterests()
    );
}

public TripResponse updateTrip(Long id, CreateTripRequest request) {

    Trip trip = tripRepository.findById(id)
            .orElseThrow(() -> new TripNotFoundException("Trip not found"));

    trip.setDestination(request.getDestination());
    trip.setDays(request.getDays());
    trip.setBudget(request.getBudget());
    trip.setInterests(request.getInterests());

    Trip updatedTrip = tripRepository.save(trip);

    return new TripResponse(
            updatedTrip.getId(),
            updatedTrip.getDestination(),
            updatedTrip.getDays(),
            updatedTrip.getBudget(),
            updatedTrip.getInterests()
    );
}

public void deleteTrip(Long id) {

    Trip trip = tripRepository.findById(id)
            .orElseThrow(() -> new TripNotFoundException("Trip not found"));

    tripRepository.delete(trip);
}
    
}

package com.planner.TourVista.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.TourVista.Entity.Trip;
import com.planner.TourVista.Exception.TripNotFoundException;
import com.planner.TourVista.Repository.TripRepository;
import com.planner.TourVista.dto.CreateTripRequest;
import com.planner.TourVista.dto.TripResponse;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    @Test
void getTripById_shouldReturnTripResponse() {

    Trip trip = new Trip();
    trip.setDestination("London");
    trip.setDays(7);
    trip.setBudget(2000);

    when(tripRepository.findById(1L))
            .thenReturn(java.util.Optional.of(trip));

    TripResponse response = tripService.getTripById(1L);

    assertEquals("London", response.getDestination());
    assertEquals(7, response.getDays());
    assertEquals(2000, response.getBudget());

    verify(tripRepository).findById(1L);
}

    @Test
    void createTrip_shouldCreateAndReturnTripResponse() {

        CreateTripRequest request = new CreateTripRequest();
        request.setDestination("Paris");
        request.setDays(5);
        request.setBudget(1000);

        Trip savedTrip = new Trip();
        savedTrip.setDestination("Paris");
        savedTrip.setDays(5);
        savedTrip.setBudget(1000);

        when(tripRepository.save(any(Trip.class)))
                .thenReturn(savedTrip);

        TripResponse response = tripService.createTrip(request);

        assertEquals("Paris", response.getDestination());
        assertEquals(5, response.getDays());
        assertEquals(1000, response.getBudget());

        verify(tripRepository).save(any(Trip.class));
    }

    @Test
void getTripById_shouldThrowExceptionWhenTripDoesNotExist() {

    when(tripRepository.findById(999L))
            .thenReturn(java.util.Optional.empty());

    TripNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
            TripNotFoundException.class,
            () -> tripService.getTripById(999L)
    );

    assertEquals("Trip not found", exception.getMessage());

    verify(tripRepository).findById(999L);
}

@Test
void updateTrip_shouldUpdateAndReturnTripResponse() {

    Trip existingTrip = new Trip();
    existingTrip.setDestination("Paris");
    existingTrip.setDays(5);
    existingTrip.setBudget(1000);

    CreateTripRequest request = new CreateTripRequest();
    request.setDestination("London");
    request.setDays(7);
    request.setBudget(2000);

    when(tripRepository.findById(1L))
            .thenReturn(java.util.Optional.of(existingTrip));

    when(tripRepository.save(any(Trip.class)))
            .thenReturn(existingTrip);

    TripResponse response = tripService.updateTrip(1L, request);

    assertEquals("London", response.getDestination());
    assertEquals(7, response.getDays());
    assertEquals(2000, response.getBudget());

    verify(tripRepository).findById(1L);
    verify(tripRepository).save(existingTrip);
}

@Test
void updateTrip_shouldThrowExceptionWhenTripDoesNotExist() {

    CreateTripRequest request = new CreateTripRequest();
    request.setDestination("London");
    request.setDays(7);
    request.setBudget(2000);

    when(tripRepository.findById(999L))
            .thenReturn(java.util.Optional.empty());

    TripNotFoundException exception =
            org.junit.jupiter.api.Assertions.assertThrows(
                    TripNotFoundException.class,
                    () -> tripService.updateTrip(999L, request)
            );

    assertEquals("Trip not found", exception.getMessage());

    verify(tripRepository).findById(999L);
}

@Test
void deleteTrip_shouldDeleteExistingTrip() {

    Trip trip = new Trip();
    trip.setDestination("Paris");
    trip.setDays(5);
    trip.setBudget(1000);

    when(tripRepository.findById(1L))
            .thenReturn(java.util.Optional.of(trip));

    tripService.deleteTrip(1L);

    verify(tripRepository).findById(1L);
    verify(tripRepository).delete(trip);
}

@Test
void deleteTrip_shouldThrowExceptionWhenTripDoesNotExist() {

    when(tripRepository.findById(999L))
            .thenReturn(java.util.Optional.empty());

    TripNotFoundException exception =
            org.junit.jupiter.api.Assertions.assertThrows(
                    TripNotFoundException.class,
                    () -> tripService.deleteTrip(999L)
            );

    assertEquals("Trip not found", exception.getMessage());

    verify(tripRepository).findById(999L);
}


}
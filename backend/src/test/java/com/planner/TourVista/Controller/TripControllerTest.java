package com.planner.TourVista.Controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.planner.TourVista.Exception.TripNotFoundException;
import com.planner.TourVista.Services.TripService;
import com.planner.TourVista.dto.TripResponse;

@WebMvcTest(TripController.class)
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TripService tripService;

    @Test
    void createTrip_shouldReturn200ForValidRequest() throws Exception {

        TripResponse response =
                new TripResponse(1L, "Paris", 5, 1000, List.of());

        when(tripService.createTrip(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "destination": "Paris",
                                    "days": 5,
                                    "budget": 1000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destination").value("Paris"))
                .andExpect(jsonPath("$.days").value(5))
                .andExpect(jsonPath("$.budget").value(1000));
    }

    @Test
void createTrip_shouldReturn400ForInvalidRequest() throws Exception {

    mockMvc.perform(post("/api/trips")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "destination": "",
                                "days": 0,
                                "budget": 0
                            }
                            """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.destination").value("Destination is required"))
            .andExpect(jsonPath("$.days").value("Days must be greater than 0"))
            .andExpect(jsonPath("$.budget").value("Budget must be greater than 0"));
}

@Test
void getTripById_shouldReturn200() throws Exception {

    TripResponse response =
            new TripResponse(1L, "Paris", 5, 1000, List.of());

    when(tripService.getTripById(1L))
            .thenReturn(response);

    mockMvc.perform(get("/api/trips/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.destination").value("Paris"))
            .andExpect(jsonPath("$.days").value(5))
            .andExpect(jsonPath("$.budget").value(1000));
}

@Test
void getTripById_shouldReturn404WhenTripDoesNotExist() throws Exception {

    when(tripService.getTripById(999L))
            .thenThrow(new TripNotFoundException("Trip not found"));

    mockMvc.perform(get("/api/trips/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Trip not found"));
}

@Test
void getAllTrips_shouldReturn200() throws Exception {

    TripResponse trip1 =
            new TripResponse(1L, "Paris", 5, 1000, List.of());

    TripResponse trip2 =
            new TripResponse(2L, "London", 7, 2000, List.of());

    when(tripService.getAllTrips())
            .thenReturn(java.util.List.of(trip1, trip2));

    mockMvc.perform(get("/api/trips"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].destination").value("Paris"))
            .andExpect(jsonPath("$[1].destination").value("London"));
}

@Test
void updateTrip_shouldReturn200() throws Exception {

    TripResponse response =
            new TripResponse(1L, "London", 7, 2000,List.of());

    when(tripService.updateTrip(
            org.mockito.ArgumentMatchers.eq(1L),
            any()
    )).thenReturn(response);

    mockMvc.perform(put("/api/trips/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "destination": "London",
                                "days": 7,
                                "budget": 2000
                            }
                            """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.destination").value("London"))
            .andExpect(jsonPath("$.days").value(7))
            .andExpect(jsonPath("$.budget").value(2000));
}

@Test
void deleteTrip_shouldReturn204() throws Exception {

    mockMvc.perform(delete("/api/trips/1"))
            .andExpect(status().isNoContent());
}

@Test
void deleteTrip_shouldReturn404WhenTripDoesNotExist() throws Exception {

    org.mockito.Mockito.doThrow(
            new TripNotFoundException("Trip not found")
    ).when(tripService).deleteTrip(999L);

    mockMvc.perform(delete("/api/trips/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Trip not found"));
}
    
}
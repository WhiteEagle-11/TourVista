package com.planner.TourVista.Repository;

import com.planner.TourVista.Entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
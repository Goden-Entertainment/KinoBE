package org.example.kinobe.repository;

import org.example.kinobe.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}

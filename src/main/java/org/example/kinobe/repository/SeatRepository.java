package org.example.kinobe.repository;

import org.example.kinobe.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByTheater_TheaterId(int theaterId);
}

package org.example.kinobe.service;

import org.example.kinobe.model.Seat;

import java.util.List;

public interface SeatService {
    Seat createSeat(Seat seat);
    List<Seat> readAllSeat();
    List<Seat> readSeatsByTheaterId(int theaterId);
    Seat readSeatById(int seatId);
    Seat updateSeat(int seatId, Seat updateSeat);
    void deleteSeat(int seatId);
}

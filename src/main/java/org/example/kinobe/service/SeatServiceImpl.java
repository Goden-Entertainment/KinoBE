package org.example.kinobe.service;

import org.example.kinobe.model.Seat;
import org.example.kinobe.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{

    @Autowired
    SeatRepository seatRepository;

    @Override
    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> readAllSeat() {
        return seatRepository.findAll();
    }

    @Override
    public Seat readSeatById(int seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));
    }

    @Override
    public Seat updateSeat(int seatId, Seat updateSeat) {
        Seat existingSeat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));

        existingSeat.setSeatRow(updateSeat.getSeatRow());
        existingSeat.setSeatNumber(updateSeat.getSeatNumber());
        existingSeat.setPrice(updateSeat.getPrice());
        existingSeat.setTheater(updateSeat.getTheater());

        return seatRepository.save(existingSeat);
    }

    @Override
    public void deleteSeat(int seatId) {
        seatRepository.deleteById(seatId);
    }
}

package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.model.Seat;
import org.example.kinobe.service.SeatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
@CrossOrigin("*")
public class SeatController {

    @Autowired
    private SeatServiceImpl seatServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Seat savedSeat = seatServiceImpl.createSeat(seat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSeat);
    }

    @GetMapping("/seats")
    public ResponseEntity<List<Seat>> readAllSeats() {
        return ResponseEntity.ok(seatServiceImpl.readAllSeat());
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Seat>> readSeatsByTheaterId(@PathVariable int theaterId) {
        return ResponseEntity.ok(seatServiceImpl.readSeatsByTheaterId(theaterId));
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<Seat> readSeatById(@PathVariable int seatId, HttpSession session) {

        Seat seat = seatServiceImpl.readSeatById(seatId);
        if (seat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(seat);
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<Seat> updateSeat(@PathVariable int seatId, @RequestBody Seat updatedSeat, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Seat seat = seatServiceImpl.updateSeat(seatId, updatedSeat);
        return ResponseEntity.ok(seat);
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable int seatId, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (seatServiceImpl.readSeatById(seatId) == null) {
            return ResponseEntity.notFound().build();
        }
        seatServiceImpl.deleteSeat(seatId);
        return ResponseEntity.noContent().build();
    }
}
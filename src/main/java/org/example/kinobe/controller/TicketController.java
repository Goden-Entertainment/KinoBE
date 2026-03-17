package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.model.Ticket;
import org.example.kinobe.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketServiceImpl.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> readAllTickets() {
        return ResponseEntity.ok(ticketServiceImpl.readAllTicket());
    }

    @GetMapping("/showing/{showingId}")
    public ResponseEntity<List<Ticket>> readTicketsByShowingId(@PathVariable int showingId) {
        return ResponseEntity.ok(ticketServiceImpl.readTicketsByShowingId(showingId));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> readTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketServiceImpl.readTicketById(ticketId);
        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int ticketId, @RequestBody Ticket updatedTicket) {
        Ticket ticket = ticketServiceImpl.updateTicket(ticketId, updatedTicket);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int ticketId) {
        if (ticketServiceImpl.readTicketById(ticketId) == null) {
            return ResponseEntity.notFound().build();
        }
        ticketServiceImpl.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}
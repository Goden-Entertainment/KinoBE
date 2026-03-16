package org.example.kinobe.service;

import org.example.kinobe.model.Ticket;
import org.example.kinobe.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {


    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> readAllTicket() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> readTicketsByShowingId(int showingId) {
        return ticketRepository.findAllByShowing_ShowingId(showingId);
    }

    @Override
    public Ticket readTicketById(int ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
    }

    @Override
    public Ticket updateTicket(int ticketId, Ticket updateTicket) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        existingTicket.setShowing(updateTicket.getShowing());
        existingTicket.setSeat(updateTicket.getSeat());

        return ticketRepository.save(existingTicket);
    }

    @Override
    public void deleteTicket(int ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}

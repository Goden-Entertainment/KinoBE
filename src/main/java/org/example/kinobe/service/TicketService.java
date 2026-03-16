package org.example.kinobe.service;

import org.example.kinobe.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    List<Ticket> readAllTicket();
    List<Ticket> readTicketsByShowingId(int showingId);
    Ticket readTicketById(int ticketId);
    Ticket updateTicket(int ticketId, Ticket updateTicket);
    void deleteTicket(int ticketId);


}

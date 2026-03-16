package org.example.kinobe.repository;

import org.example.kinobe.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByShowing_ShowingId(int showingId);
}

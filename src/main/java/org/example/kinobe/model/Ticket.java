package org.example.kinobe.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    @ManyToOne
    @JoinColumn(name = "showingFK")
    Showing showing;

    @ManyToOne
    @JoinColumn(name = "seatFK")
    Seat seat;

    public Ticket(int ticketId, Showing showing, Seat seat) {
        this.ticketId = ticketId;
        this.showing = showing;
        this.seat = seat;
    }

    public Ticket() {

    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}

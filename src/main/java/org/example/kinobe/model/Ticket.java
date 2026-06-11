package org.example.kinobe.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
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
}

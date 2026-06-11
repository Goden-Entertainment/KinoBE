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
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;
    private Integer seatRow;
    private Integer seatNumber;
    private float price;

    @ManyToOne
    @JoinColumn(name = "theaterFK")
    Theater theater;

    public Seat(int seatId, int seatRow, int seatNumber, float price, Theater theater) {
        this.seatId = seatId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.price = price;
        this.theater = theater;
    }
}

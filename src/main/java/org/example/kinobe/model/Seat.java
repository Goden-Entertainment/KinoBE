package org.example.kinobe.model;

import jakarta.persistence.*;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private int seatRow;
    private int seatNumber;
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

    public Seat() {

    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}

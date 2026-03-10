package org.example.kinobe.model;

public class Order {

    int orderId;
    Showing showing;
    Ticket ticket;

    public Order(int orderId, Showing showing, Ticket ticket) {
        this.orderId = orderId;
        this.showing = showing;
        this.ticket = ticket;
    }
    public Order(){
        
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}

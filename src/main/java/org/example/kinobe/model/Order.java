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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "showingFK")
    private Showing showing;

    @ManyToOne
    @JoinColumn(name = "userFK")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order(int orderId, Showing showing, Ticket ticket) {
        this.orderId = orderId;
        this.showing = showing;
        this.user = user;
    }}

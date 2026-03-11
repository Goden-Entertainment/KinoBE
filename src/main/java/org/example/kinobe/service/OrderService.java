package org.example.kinobe.service;

import org.example.kinobe.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> readAllOrder();
    Order readOrderById(int orderId);
    Order updateOrder(int orderId, Order updateOrder);
    void deleteOrder(int orderId);
}

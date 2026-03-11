package org.example.kinobe.service;

import org.example.kinobe.model.Order;
import org.example.kinobe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Override
    public List<Order> readAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order readOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public Order updateOrder(int orderId, Order updateOrder) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        existingOrder.setShowing(updateOrder.getShowing());
        existingOrder.setUser(updateOrder.getUser());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(int orderId) {
        if(!orderRepository.existsById(orderId)){
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}

package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.model.Order;
import org.example.kinobe.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderServiceImpl.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> readAllOrders() {
        return ResponseEntity.ok(orderServiceImpl.readAllOrder());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> readOrderById(@PathVariable int orderId) {
        Order order = orderServiceImpl.readOrderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId, @RequestBody Order updatedOrder) {
        Order order = orderServiceImpl.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        orderServiceImpl.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
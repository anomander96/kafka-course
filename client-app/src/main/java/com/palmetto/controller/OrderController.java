package com.palmetto.controller;

import com.palmetto.model.Order;
import com.palmetto.model.OrderRequest;
import com.palmetto.model.OrderResponse;
import com.palmetto.repository.OrderRepository;
import com.palmetto.service.ClientOrderService;
import com.palmetto.service.data.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    @Autowired
    private OrderDataService orderDataService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public OrderResponse getOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return clientOrderService.process(orderRequest);
    }

    @GetMapping("/order/{id}/status")
    public String getStatus(@PathVariable("id") Long orderId) {
        Optional<Order> order = orderRepository.getStatusByOrderId(orderId);
        if (order.isPresent()) {
            return "Order status: " + order.get().getOrderStatus().name();
        } else {
            return "There's no such order with order_id=" + orderId;
        }
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

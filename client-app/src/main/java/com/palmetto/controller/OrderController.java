package com.palmetto.controller;

import com.palmetto.model.Order;
import com.palmetto.service.data.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    @Autowired
    private OrderDataService orderDataService;

    @GetMapping("/order/{orderId}")
    public Order getOrder(@PathVariable long orderId) {
        // Logger needed
        System.out.println(orderId);
        return orderDataService.getOrderById(orderId);
    }
}

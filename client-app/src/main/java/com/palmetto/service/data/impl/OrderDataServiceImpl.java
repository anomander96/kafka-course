package com.palmetto.service.data.impl;

import com.palmetto.model.Order;
import com.palmetto.repository.OrderRepository;
import com.palmetto.service.data.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDataServiceImpl implements OrderDataService{

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order addOrder(Order order) {
        // Logger needed
        System.out.println("Adding: " + order + " to DB");
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public Order updateOrder(Order order) {
        Order orderFromDb = orderRepository.save(order);
        // Logger needed
        System.out.println("Updating: " + order + " in DB");
        return orderFromDb;
    }
}

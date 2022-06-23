package com.palmetto.service.data.impl;

import com.palmetto.exception.NoSuchOrderException;
import com.palmetto.model.Order;
import com.palmetto.model.OrderStatus;
import com.palmetto.repository.OrderRepository;
import com.palmetto.service.data.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void updateStatus(Long orderId, OrderStatus orderStatus) {
        Optional<Order> ordersOptional = orderRepository.getOrderById(orderId);
        Order order;
        if (ordersOptional.isPresent()) {
            order = ordersOptional.get();
            order.setOrderStatus(orderStatus);
        } else {
            throw new NoSuchOrderException(String.format("There's no order with such id=%d", orderId));
        }
        orderRepository.save(order);
    }
}

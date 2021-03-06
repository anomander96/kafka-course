package com.palmetto.service.data.impl;

import com.palmetto.data.OrderData;
import com.palmetto.model.Order;
import com.palmetto.model.OrderRequest;
import com.palmetto.model.OrderResponse;
import com.palmetto.model.OrderStatus;
import com.palmetto.repository.OrderRepository;
import com.palmetto.service.data.OrderDataService;
import com.palmetto.service.kafka.producer.ClientOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderDataServiceImpl implements OrderDataService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientOrderProducer clientOrderProducer;

    @Override
    @Transactional
    public OrderResponse process(OrderRequest orderRequest) {
        Order order = orderRepository.save(buildOrder(orderRequest));
        clientOrderProducer.sendOrder(buildOrderData(order));
        return order.toDto();
    }

    private OrderData buildOrderData(Order order) {
        return OrderData.builder()
                .orderId(order.getOrderId())
                .pizzaName(order.getPizzaName())
                .status(order.getOrderStatus())
                .build();
    }

    private Order buildOrder(OrderRequest orderRequest) {
        return Order.builder()
                .pizzaName(orderRequest.getPizzaName())
                .orderStatus(OrderStatus.ORDER_PLACED)
                .build();
    }
}

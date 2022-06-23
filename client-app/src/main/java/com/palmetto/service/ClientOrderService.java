package com.palmetto.service;

import com.palmetto.data.OrderData;
import com.palmetto.model.Order;
import com.palmetto.model.OrderRequest;
import com.palmetto.model.OrderResponse;
import com.palmetto.model.OrderStatus;
import com.palmetto.repository.OrderRepository;
import com.palmetto.service.kafka.producer.ClientOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class ClientOrderService {

    @Autowired
    private ClientOrderProducer clientOrderProducer;

    @Autowired
    private OrderRepository orderRepository;

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

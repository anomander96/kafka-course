package com.palmetto.service;

import com.palmetto.data.OrderData;
import com.palmetto.kafka.producer.CourierOrderProducer;
import com.palmetto.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {

    private final CourierOrderProducer producer;

    public void process(OrderData orderData) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        orderData.setStatus(OrderStatus.READY_FOR_PICKUP);
        producer.sendEvent(orderData);
        log.info(
                "Event with orderId={} has been sent to notification-topic with status={}",
                orderData.getOrderId(),
                orderData.getStatus());
    }
}

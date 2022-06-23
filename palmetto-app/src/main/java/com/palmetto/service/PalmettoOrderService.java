package com.palmetto.service;

import com.palmetto.data.OrderData;
import com.palmetto.kafka.producer.PalmettoOrderProducer;
import com.palmetto.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PalmettoOrderService {
    private final PalmettoOrderProducer producer;

    public void process(OrderData orderData) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        orderData.setStatus(OrderStatus.READY_FOR_PICKUP);
        producer.sendEvent(orderData);
        log.info("Event has been sent to notification-topic.");
    }
}

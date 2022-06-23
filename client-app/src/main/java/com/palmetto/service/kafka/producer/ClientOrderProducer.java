package com.palmetto.service.kafka.producer;

import com.palmetto.data.OrderData;
import com.palmetto.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientOrderProducer {

    private final KafkaTemplate<Long, OrderData> kafkaTemplate;

    private static final String TOPIC = "order-topic";

    public void sendOrder(OrderData orderData) {
        setMDCCorrelationId(orderData);
        kafkaTemplate.send(buildRecord(orderData));
        log.info("Order has been sent. ORDER_ID={}", orderData.getOrderId());
    }

    private void setMDCCorrelationId(OrderData orderData) {
        MDCUtil.setCorrelationId(TOPIC, orderData.getOrderId(), orderData.getPizzaName());
        orderData.setCorrelationId(MDCUtil.getCorrelationId());
    }

    private ProducerRecord<Long, OrderData> buildRecord(OrderData orderData) {
        return new ProducerRecord<>(TOPIC, orderData.getOrderId(), orderData);
    }
}

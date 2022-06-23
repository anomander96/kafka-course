package com.palmetto.kafka.producer;

import com.palmetto.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierOrderProducer {

    private final KafkaTemplate<Long, OrderData> kafkaTemplate;

    @Value("${app.kafka.notification.topic}")
    private String topic;

    public void sendEvent(OrderData orderData) {
        kafkaTemplate.send(buildRecord(orderData));
    }

    private ProducerRecord<Long, OrderData> buildRecord(OrderData orderData) {
        return new ProducerRecord<>(topic, orderData.getOrderId(), orderData);
    }
}

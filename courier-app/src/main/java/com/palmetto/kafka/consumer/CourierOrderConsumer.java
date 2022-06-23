package com.palmetto.kafka.consumer;

import com.palmetto.data.OrderData;
import com.palmetto.service.CourierService;
import com.palmetto.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierOrderConsumer {

    @Autowired
    private CourierService courierService;

    @KafkaListener(
            topics = "${app.kafka.notification.topic}",
            groupId = "${app.kafka.notification.group}",
            containerFactory = "containerFactory")
    public void orderHandler(ConsumerRecord<Long, OrderData> consumerRecord) {
        try {
            OrderData orderData = consumerRecord.value();
            MDCUtil.setCorrelationId(orderData.getCorrelationId());
            log.info("Order data received. RECORD={}", consumerRecord);

            courierService.process(orderData);
            log.info("Order with orderId={} is ready to pickup", orderData.getOrderId());
        } catch (Exception e) {
            log.error("Error while while picking up an order", e);
        } finally {
            MDC.clear();
        }
    }
}

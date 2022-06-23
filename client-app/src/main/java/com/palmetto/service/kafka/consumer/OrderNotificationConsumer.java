package com.palmetto.service.kafka.consumer;

import com.palmetto.data.OrderData;
import com.palmetto.service.data.OrderStatusService;
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
public class OrderNotificationConsumer {
    @Autowired
    private OrderStatusService orderStatusService;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "client-app-group",
            containerFactory = "containerFactory")
    public void orderNotificationHandler(ConsumerRecord<Long, OrderData> consumerRecord) {
        try {
            OrderData orderEvent = consumerRecord.value();
            MDCUtil.setCorrelationId(orderEvent.getCorrelationId());
            log.info("Event received. ConsumerRecord={}", consumerRecord);
            orderStatusService.updateStatus(orderEvent.getOrderId(), orderEvent.getStatus());
        } catch (RuntimeException e) {
            log.error("Error while updating order status.", e);
        } finally {
            MDC.clear();
        }
    }
}

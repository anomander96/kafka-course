package com.palmetto.kafka.consumer;

import com.palmetto.data.OrderData;
import com.palmetto.service.PalmettoOrderService;
import com.palmetto.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PalmettoOrderConsumer {

    private final PalmettoOrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.order.topic}",
            groupId = "${app.kafka.order.group}",
            containerFactory = "containerFactory")
    public void orderHandler(ConsumerRecord<Long, OrderData> consumerRecord) {
        try {
            OrderData orderEvent = consumerRecord.value();
            MDCUtil.setCorrelationId(orderEvent.getCorrelationId());
            log.info("Event has been received. {}", orderEvent);
            orderService.process(orderEvent);
        } catch (Exception e) {
            log.error("Error while order processing.", e);
        } finally {
            MDC.clear();
        }
    }
}

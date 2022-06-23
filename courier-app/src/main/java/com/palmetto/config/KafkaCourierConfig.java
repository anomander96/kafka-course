package com.palmetto.config;

import com.palmetto.data.OrderData;
import com.palmetto.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaCourierConfig {

    private final KafkaProperties properties;

    @Bean
    public ConsumerFactory<Long, OrderData> consumerFactory() {
        Map<String, Object> consumerConfig = properties.buildConsumerProperties();
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderData.class);
        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, Boolean.FALSE);
        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, OrderData> containerFactory(
            ConsumerFactory<Long, OrderData> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, OrderData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setRecordFilterStrategy(skipEventWithStatusDeliveredFilterStrategy());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public RecordFilterStrategy<Long, OrderData> skipEventWithStatusDeliveredFilterStrategy() {
        return consumerRecord -> {
            if (consumerRecord.value().getStatus() == OrderStatus.READY_FOR_PICKUP) {
                log.info("Event has been skipped. Event status={}", consumerRecord.value().getStatus());
                return true;
            }
            return false;
        };
    }
}


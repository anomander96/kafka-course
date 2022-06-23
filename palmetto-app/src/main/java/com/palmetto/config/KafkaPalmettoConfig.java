package com.palmetto.config;

import com.palmetto.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaPalmettoConfig {

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
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

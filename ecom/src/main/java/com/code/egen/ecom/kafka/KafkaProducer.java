package com.code.egen.ecom.kafka;

import com.code.egen.ecom.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, List<Order>> kafkaTemplate;

    public void sendOrdersToTopic(String topic, List<Order> orderEntities) {
        kafkaTemplate.send(topic, orderEntities);
    }

}
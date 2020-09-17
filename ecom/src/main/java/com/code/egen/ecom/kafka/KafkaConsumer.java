package com.code.egen.ecom.kafka;

import com.code.egen.ecom.config.KafkaConfig;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.exception.AddressNotFoundException;
import com.code.egen.ecom.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class KafkaConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "order-test", containerFactory = "orderKafkaListenerFactory")
    public void consumeJson(List<OrderEntity> orderEntities) throws AddressNotFoundException {
/*        System.out.println("List is: " + orderEntities);
        for(OrderEntity each:orderEntities){
            System.out.println(each);
        }*/
        orderService.addBulkOrders(orderEntities);
    }
}
package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.service.OrderService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class OrderController extends BaseRestController{
    private final OrderService orderService;

    @GetMapping(value = "/order/{id}")
    public OrderEntity getOrderById(@PathVariable("id") Long orderId){
        return orderService.getOrderById(orderId);
    }

    @PostMapping(value = "/order")
    public void createOrder(@RequestBody OrderEntity orderEntity){
        orderService.addOrder(orderEntity);
    }

}

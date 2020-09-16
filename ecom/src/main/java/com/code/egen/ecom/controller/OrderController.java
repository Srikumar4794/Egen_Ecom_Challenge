package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController extends BaseRestController{
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable("id") Long orderId){
        try{
            return ResponseEntity.ok(orderService.getOrderById(orderId));
        }
        catch(IllegalArgumentException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(value = "/order")
    public void createOrder(@RequestBody OrderEntity orderEntity){
        orderService.addOrder(orderEntity);
    }

    @PatchMapping(value = "/order/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") Long orderId, @RequestBody OrderEntity orderEntity){
        try{
            orderService.cancelOrder(orderId, orderEntity);
            return ResponseEntity.ok().build();
        }
        catch(IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}

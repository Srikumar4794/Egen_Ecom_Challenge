package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.exception.OrderNotFoundException;
import com.code.egen.ecom.service.OrderService;
import com.code.egen.ecom.translator.IOrderTranslator;
import com.code.egen.ecom.vo.OrderVO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class OrderController extends BaseRestController{
    private final OrderService orderService;
    private final IOrderTranslator orderTranslator;

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<OrderVO> getOrderById(@PathVariable("id") Long orderId){
        try{
            OrderVO body = orderTranslator.toOrderVO(orderService.getOrderById(orderId));
            return ResponseEntity.ok(body);
        }
        catch(OrderNotFoundException exception){
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
        catch(OrderNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}

package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.exception.AddressNotFoundException;
import com.code.egen.ecom.exception.OrderNotFoundException;
import com.code.egen.ecom.service.OrderService;
import com.code.egen.ecom.translator.IOrderTranslator;
import com.code.egen.ecom.vo.OrderVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class OrderController {
    private final OrderService orderService;
    private final IOrderTranslator orderTranslator;

    @GetMapping(value = "/api/v1/order/{id}")
    @ApiOperation(value = "Get order by id.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order found."),
            @ApiResponse(code = 400, message = "Order details not found.")})
    public ResponseEntity<OrderVO> getOrderById(@PathVariable("id") Long orderId) {
        try {
            OrderVO body = orderTranslator.toOrderVO(orderService.getOrderById(orderId));
            return ResponseEntity.ok(body);
        } catch (OrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "/api/v1/order")
    @ApiOperation(value = "Create new order.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order successfully created."),
            @ApiResponse(code = 400, message = "Order details not found")
    })
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity orderEntity) {
        try{
            OrderEntity response = orderService.addOrder(orderEntity);
            return ResponseEntity.ok(response);
        }
        catch(AddressNotFoundException addressNotFoundException){
            return ResponseEntity.badRequest().build();
        }

    }

    @PatchMapping(value = "/api/v1/order/{id}/cancel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order successfully cancelled."),
            @ApiResponse(code = 400, message = "Order details not found.")})
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") Long orderId, @RequestBody OrderEntity orderEntity) {
        try {
            orderService.cancelOrder(orderId, orderEntity);
            return ResponseEntity.ok().build();
        } catch (OrderNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}

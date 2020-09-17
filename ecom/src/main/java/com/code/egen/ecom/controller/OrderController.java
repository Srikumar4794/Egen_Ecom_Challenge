package com.code.egen.ecom.controller;

import com.code.egen.ecom.dto.BatchOrderDTO;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.exception.AddressNotFoundException;
import com.code.egen.ecom.exception.OrderNotFoundException;
import com.code.egen.ecom.kafka.KafkaProducer;
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

import java.util.List;

@RestController
@Data
public class OrderController {
    private final OrderService orderService;
    private final IOrderTranslator orderTranslator;
    private final KafkaProducer kafkaProducer;

    @GetMapping(value = "/api/v1/order/{id}")
    @ApiOperation(value = "Get order by id.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order found."),
            @ApiResponse(code = 400, message = "Order details not found.")})
    public ResponseEntity<OrderVO> getOrderById(@PathVariable("id") Long orderId) {
        try {
            OrderVO body = orderTranslator.toOrderVO(orderService.getOrderById(orderId));
            return ResponseEntity.ok(body);
        }
        catch (OrderNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "/api/v1/order")
    @ApiOperation(value = "Create new order.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order successfully created."),
            @ApiResponse(code = 400, message = "Order details not found")
    })
    public ResponseEntity<OrderVO> createOrder(@RequestBody OrderEntity orderEntity) {
        try {
            OrderEntity response = orderService.addOrder(orderEntity);
            return ResponseEntity.ok(orderTranslator.toOrderVO(response));
        }
        catch (AddressNotFoundException addressNotFoundException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/api/v1/order-bulk")
    @ApiOperation(value = "Create a group of orders.")
    @ApiResponse(code = 202, message = "Accepted")
    public void createOrders(@RequestBody List<OrderEntity> orderEntities){
        kafkaProducer.sendOrdersToTopic("order-test",orderEntities);
//    public void createOrders(@RequestBody BatchOrderDTO batchOrderDTO){
//        try {
//            orderService.addBulkOrders(batchOrderDTO.getOrderEntityList());
//        } catch (AddressNotFoundException addressNotFoundException) {
//            addressNotFoundException.printStackTrace();
//        }

    }


    @PatchMapping(value = "/api/v1/order/{id}/cancel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order successfully cancelled."),
            @ApiResponse(code = 400, message = "Order details not found.")})
    public ResponseEntity<OrderVO> cancelOrder(@PathVariable("id") Long orderId, @RequestBody OrderEntity orderEntity) {
        try {
            OrderEntity responseEntity = orderService.cancelOrder(orderId, orderEntity);
            return ResponseEntity.ok(orderTranslator.toOrderVO(responseEntity));
        }
        catch (OrderNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}

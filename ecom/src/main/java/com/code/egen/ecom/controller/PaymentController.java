package com.code.egen.ecom.controller;

import com.code.egen.ecom.dto.PaymentDTO;
import com.code.egen.ecom.exception.PaymentException;
import com.code.egen.ecom.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/v1/pay")
    @ApiOperation("Make payment for an order")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Transaction successfully completed"),
                           @ApiResponse(code = 404, message = "Order details not found.") })
    public ResponseEntity<Void> payForOrder(@RequestBody PaymentDTO paymentDTO){
        try{
            paymentService.addPayment(paymentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch(PaymentException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}

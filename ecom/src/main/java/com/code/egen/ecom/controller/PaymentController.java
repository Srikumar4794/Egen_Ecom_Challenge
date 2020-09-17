package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.PaymentEntity;
import com.code.egen.ecom.exception.PaymentException;
import com.code.egen.ecom.service.PaymentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class PaymentController extends BaseRestController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Void> payForOrder(@RequestBody PaymentEntity paymentEntity){
        try{
            paymentService.addPayment(paymentEntity);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch(PaymentException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}

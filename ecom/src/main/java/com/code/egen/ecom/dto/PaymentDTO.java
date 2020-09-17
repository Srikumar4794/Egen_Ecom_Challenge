package com.code.egen.ecom.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long orderId;
    private Long billingAddressId;
    private String paymentMethod;
    private Double paymentAmount;
}

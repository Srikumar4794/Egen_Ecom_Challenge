package com.code.egen.ecom.enums;

import lombok.Getter;

public enum ErrorCodes {
    ORDER_NOT_FOUND("Order not found because of invalid order details."),
    INVALID_PAYMENT_DETAILS("Incorrect payment amount details");

    @Getter
    private String errorDesc;

    ErrorCodes(String desc){
        this.errorDesc = desc;
    }
}

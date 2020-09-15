package com.code.egen.ecom.enums;
import lombok.Getter;

public enum OrderStatusCodes {
    CREATED("created"),
    PAID("paid"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELLED("cancelled");

    @Getter
    private String desc;

    OrderStatusCodes(String desc){
        this.desc = desc;
    }
}

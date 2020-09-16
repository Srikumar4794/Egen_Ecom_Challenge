package com.code.egen.ecom.vo;

import com.code.egen.ecom.entity.ItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrderVO {
    private Long orderId;
    private Long customerId;
    private List<ItemEntity> orderItemEntities;
    private String shippingAddrLine1;
    private String shippingAddrLine2;
    private String city;
    private String state;
    private String zipCode;
}

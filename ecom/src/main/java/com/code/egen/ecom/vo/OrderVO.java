package com.code.egen.ecom.vo;

import com.code.egen.ecom.entity.ItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrderVO {
    private Long orderId;
    private Long customerId;
    private List<ItemEntity> orderItemEntities;
    private String shippingAddress;
    private Double orderSubTotal;
    private Double orderTax;
    private Double shippingCharges;
}

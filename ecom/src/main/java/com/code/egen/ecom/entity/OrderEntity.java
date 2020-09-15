package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
@Data
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @JoinColumn(name = "order_item_id", referencedColumnName = "order_item_id", insertable = false, updatable = false)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity")
    List<OrderItemEntity> orderItemEntityList;

    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity")
    List<OrderPaymentEntity> orderPaymentEntityList;

    @Column(name = "customer_name")
    private Long customerName;

    @Column(name = "billing_addr_id")
    private Long billingAddressId;

    @Column(name = "shipping_addr_id")
    private Long shippingAddressId;

    @Column(name = "order_subtotal")
    private Double orderSubTotal;

    @Column(name = "order_tax")
    private Double orderTax;

    @Column(name = "shipping_charge")
    private Double shippingCharges;

    @Column(name = "order_total")
    private Double orderTotal;

    @Column(name = "created_ts")
    private Date createdTimeStamp;

    @Column(name = "modified_ts")
    private Date modifiedTimeStamp;

}

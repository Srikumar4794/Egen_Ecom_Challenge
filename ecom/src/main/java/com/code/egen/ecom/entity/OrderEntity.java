package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<ItemEntity> orderItemEntities;

    @Column(name = "customer_id")
    private Long customerId;

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

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_ts")
    private Date createdTimeStamp;

    @Column(name = "modified_ts")
    private Date modifiedTimeStamp;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    //@JoinColumn(name = "shipping_addr_id", referencedColumnName = "addr_id", insertable = false, updatable = false)
    private AddressEntity addressEntity;
}

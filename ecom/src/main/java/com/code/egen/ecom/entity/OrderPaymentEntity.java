package com.code.egen.ecom.entity;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class OrderPaymentEntity {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_ts")
    private Long paymentTimeStamp;

    @Column(name = "txn_amount")
    private Double transactionAmount;

    @Column(name = "billing_addr_id")
    private Long billingAddressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderEntity;
}

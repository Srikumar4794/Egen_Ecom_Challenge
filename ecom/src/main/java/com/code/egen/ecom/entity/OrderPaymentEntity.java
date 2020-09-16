package com.code.egen.ecom.entity;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class OrderPaymentEntity {
    @Id
    @Column(name = "payment_confirmation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentConfirmationId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_ts")
    private Long paymentTimeStamp;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "billing_addr_id")
    private Long billingAddressId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_addr_id", referencedColumnName = "addr_id", insertable = false, updatable = false)
    private AddressEntity addressEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderEntity;

}

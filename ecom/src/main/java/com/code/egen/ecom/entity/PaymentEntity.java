package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
public class PaymentEntity {
    @Id
    @Column(name = "payment_confirmation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentConfirmationId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "billing_addr_id", nullable = false)
    private AddressEntity billAddressEntity;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderEntity;

    @Column(name = "payment_ts")
    private Long paymentTimeStamp;

}

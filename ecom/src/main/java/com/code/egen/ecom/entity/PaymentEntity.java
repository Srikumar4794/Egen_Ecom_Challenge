package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
@Data
public class PaymentEntity {
    @Id
    @Column(name = "payment_confirmation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentConfirmationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "billing_addr_id")
    private Long billingAddressId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_addr_id", insertable = false, updatable = false)
    private AddressEntity billAddressEntity;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderEntity;

    @Column(name = "payment_ts")
    private Date paymentTimeStamp;

}

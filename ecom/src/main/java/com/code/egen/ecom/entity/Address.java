package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @Column(name = "addr_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @Column(name = "addr_line1", nullable = false)
    private String addressLine1;

    @Column(name = "addr_line2", nullable = false)
    private String addressLine2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip", nullable = false)
    private String zipCode;

    @OneToMany(mappedBy = "shippingAddressEntity")
    private List<Order> orderEntities;

    @OneToMany(mappedBy = "billAddressEntity")
    private List<Payment> paymentEntities;
}

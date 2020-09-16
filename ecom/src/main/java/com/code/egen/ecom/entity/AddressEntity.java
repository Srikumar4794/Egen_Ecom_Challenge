package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "address")
@Data
public class AddressEntity {
    @Id
    @Column(name = "addr_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @Column(name = "addr_line1")
    private String addressLine1;

    @Column(name = "addr_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zipCode;
}

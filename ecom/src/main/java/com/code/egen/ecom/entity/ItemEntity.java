package com.code.egen.ecom.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Data
public class ItemEntity {
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderItemId;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;
}

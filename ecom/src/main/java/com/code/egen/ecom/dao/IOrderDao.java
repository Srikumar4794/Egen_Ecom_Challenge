package com.code.egen.ecom.dao;

import com.code.egen.ecom.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDao extends JpaRepository<OrderEntity, Long> {

}

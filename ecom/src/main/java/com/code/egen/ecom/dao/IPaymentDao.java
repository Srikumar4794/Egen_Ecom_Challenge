package com.code.egen.ecom.dao;

import com.code.egen.ecom.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPaymentDao extends JpaRepository<PaymentEntity, Long> {
    public List<PaymentEntity> findAllByOrderEntity_OrderId(Long orderId);
}

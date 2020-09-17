package com.code.egen.ecom.dao;

import com.code.egen.ecom.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPaymentDao extends JpaRepository<Payment, Long> {
    public List<Payment> findAllByOrderId(Long orderId);
}

package com.code.egen.ecom.dao;

import com.code.egen.ecom.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentDao extends JpaRepository<PaymentEntity, Long> {
}

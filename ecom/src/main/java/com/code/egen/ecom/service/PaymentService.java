package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.entity.PaymentEntity;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class PaymentService {
    private final IPaymentDao paymentDao;

    private final IOrderDao orderDao;

    public void addPayment(PaymentEntity paymentEntity){
        List<PaymentEntity> paymentEntities = paymentDao.findAllByOrderEntity_OrderId(paymentEntity.getOrderEntity().getOrderId());

        if(paymentEntities != null && !paymentEntities.isEmpty()){
            Double alreadyPaid = paymentEntities.stream().map(PaymentEntity::getPaymentAmount).reduce(0.0, Double::sum);
            OrderEntity orderEntity = orderDao.findById(paymentEntity.getOrderEntity().getOrderId()).orElseThrow(() -> new IllegalArgumentException("Invalid payment details"));

            if(alreadyPaid + paymentEntity.getPaymentAmount() > orderEntity.getOrderTotal())
                throw new IllegalArgumentException("Invalid payment details");
        }

        paymentDao.save(paymentEntity);
    }
}

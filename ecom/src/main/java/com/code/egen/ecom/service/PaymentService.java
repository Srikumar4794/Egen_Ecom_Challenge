package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.entity.PaymentEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Data
public class PaymentService {
    private final IPaymentDao paymentDao;

    private final IOrderDao orderDao;

    public void addPayment(PaymentEntity paymentEntity){
        List<PaymentEntity> paymentEntities = paymentDao.findAllByOrderId(paymentEntity.getOrderId());

        if(paymentEntities != null && !paymentEntities.isEmpty()){
            Double alreadyPaid = paymentEntities.stream().map(PaymentEntity::getPaymentAmount).reduce(0.0, Double::sum);
            OrderEntity orderEntity = orderDao.findById(paymentEntity.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Invalid payment details"));

            double total = alreadyPaid + paymentEntity.getPaymentAmount();
            if(total > orderEntity.getOrderTotal())
                throw new IllegalArgumentException("Invalid payment details");

            if(total == orderEntity.getOrderTotal()){
                orderEntity.setOrderStatus(OrderStatusCodes.PAID.getDesc());
                orderDao.save(orderEntity);
            }
        }
        paymentEntity.setPaymentTimeStamp(new Date());
        paymentDao.save(paymentEntity);
    }
}

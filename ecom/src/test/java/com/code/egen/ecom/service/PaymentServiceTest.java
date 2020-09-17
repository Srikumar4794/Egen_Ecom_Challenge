package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.entity.PaymentEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.PaymentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    IPaymentDao paymentDao;

    @Mock
    IOrderDao orderDao;

    @InjectMocks
    PaymentService mockPaymentService;

    @Test
    void addPayment() {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderId(1L);
        paymentEntity.setPaymentAmount(15.0);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        orderEntity.setOrderId(1L);
        orderEntity.setOrderTotal(14.0);

        PaymentEntity paidEntity = new PaymentEntity();
        paidEntity.setOrderId(1L);
        paidEntity.setPaymentAmount(13.0);

        Mockito.when(orderDao.findById(1L)).thenReturn(java.util.Optional.of(orderEntity));
        Mockito.when(paymentDao.findAllByOrderId(paymentEntity.getOrderId())).thenReturn(Arrays.asList(paidEntity));
        assertThrows(PaymentException.class, ()->mockPaymentService.addPayment(paymentEntity));
    }
}
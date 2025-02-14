package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.dto.PaymentDTO;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.entity.PaymentEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.PaymentException;
import com.code.egen.ecom.translator.IPaymentTranslator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        orderEntity.setOrderId(1L);
        orderEntity.setOrderTotal(14.0);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(1L);
        paymentDTO.setPaymentAmount(10.0);
    }
}
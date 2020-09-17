package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.dto.PaymentDTO;
import com.code.egen.ecom.entity.Order;
import com.code.egen.ecom.enums.OrderStatusCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Order order = new Order();
        order.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        order.setOrderId(1L);
        order.setOrderTotal(14.0);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(1L);
        paymentDTO.setPaymentAmount(10.0);
    }
}
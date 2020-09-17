package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private IOrderDao orderDao;

    @InjectMocks
    OrderService mockOrderService;

    @Test
    void getOrderById() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(2L);
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        Mockito.when(orderDao.findById(2L)).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderDao.findById(34L)).thenReturn(Optional.empty());
        assertEquals(orderEntity, mockOrderService.getOrderById(2L));
        assertThrows(OrderNotFoundException.class, () -> mockOrderService.getOrderById(34L));
    }

    @Test
    void addOrder() {

    }

    @Test
    void cancelOrder() {
    }
}
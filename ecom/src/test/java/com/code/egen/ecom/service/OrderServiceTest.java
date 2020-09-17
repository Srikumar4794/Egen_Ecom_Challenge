package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IAddressDao;
import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.AddressEntity;
import com.code.egen.ecom.entity.ItemEntity;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.AddressNotFoundException;
import com.code.egen.ecom.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private IOrderDao orderDao;

    @Mock
    private IAddressDao addressDao;

    @InjectMocks
    OrderService mockOrderService;

    @Test
    void getOrderById() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(2L);
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        Mockito.when(orderDao.findById(2L)).thenReturn(Optional.of(orderEntity));
        assertEquals(orderEntity, mockOrderService.getOrderById(2L));

        Mockito.when(orderDao.findById(34L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> mockOrderService.getOrderById(34L));
    }

    @Test
    void addOrder() throws AddressNotFoundException {
        Mockito.when(addressDao.findById(5L)).thenReturn(Optional.of(new AddressEntity()));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(2L);
        orderEntity.setShippingAddressId(5L);
        orderEntity.setShippingCharges(10.5);
        orderEntity.setOrderTax(5.0);

        Mockito.when(orderDao.save(orderEntity)).thenReturn(orderEntity);

        List<ItemEntity> itemEntities = Arrays.asList(new ItemEntity(null, 15.0, 2), new ItemEntity(null, 21.0, 4));
        orderEntity.setOrderItemEntities(itemEntities);
        OrderEntity orderEntity1 = mockOrderService.addOrder(orderEntity);
        assertEquals(orderEntity1.getOrderTotal(),129.5);

        orderEntity.setShippingAddressId(10L);
        Mockito.when(addressDao.findById(10L)).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> mockOrderService.addOrder(orderEntity));
    }

    @Test
    void cancelOrder() {

    }
}
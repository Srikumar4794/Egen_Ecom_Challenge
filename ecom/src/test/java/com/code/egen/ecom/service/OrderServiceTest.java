package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IAddressDao;
import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.Address;
import com.code.egen.ecom.entity.Item;
import com.code.egen.ecom.entity.Order;
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
        Order order = new Order();
        order.setOrderId(2L);
        order.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        Mockito.when(orderDao.findById(2L)).thenReturn(Optional.of(order));
        assertEquals(order, mockOrderService.getOrderById(2L));

        Mockito.when(orderDao.findById(34L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> mockOrderService.getOrderById(34L));
    }

    @Test
    void addOrder() throws AddressNotFoundException {
        Order order = new Order();
        order.setOrderId(2L);
        order.setShippingAddressId(5L);
        order.setShippingCharges(10.5);
        order.setOrderTax(5.0);

        Mockito.when(orderDao.save(order)).thenReturn(order);
        Mockito.when(addressDao.findById(5L)).thenReturn(Optional.of(new Address()));

        List<Item> itemEntities = Arrays.asList(new Item(null, 15.0, 2), new Item(null, 21.0, 4));
        order.setOrderItemEntities(itemEntities);
        Order order1 = mockOrderService.addOrder(order);
        assertEquals(129.5, order1.getOrderTotal());
        assertEquals(OrderStatusCodes.CREATED.getDesc(), order1.getOrderStatus());

        order.setShippingAddressId(10L);
        Mockito.when(addressDao.findById(10L)).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> mockOrderService.addOrder(order));
    }

    @Test
    void cancelOrder() throws AddressNotFoundException {
        Order order = new Order();
        order.setOrderId(2L);
        order.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        order.setShippingAddressId(5L);
        order.setShippingCharges(10.5);
        order.setOrderTax(5.0);

        Mockito.when(orderDao.findById(2L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> mockOrderService.cancelOrder(2L, order));

        Mockito.when(orderDao.findById(2L)).thenReturn(Optional.of(order));
        Mockito.when(orderDao.save(order)).thenReturn(order);

        assertEquals(OrderStatusCodes.CANCELLED.getDesc(), mockOrderService.cancelOrder(2L, order).getOrderStatus());

    }
}
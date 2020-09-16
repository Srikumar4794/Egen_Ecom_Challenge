package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class OrderService {
    private final IOrderDao orderDao;

    public OrderEntity getOrderById(Long orderId){
        return orderDao.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order does not exist"));
    }

    public void addOrder(OrderEntity orderEntity) {
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        orderDao.save(orderEntity);
    }

    public void cancelOrder(Long orderId, OrderEntity orderEntity) {
        if(orderDao.findById(orderId).isEmpty())
            throw new IllegalArgumentException("Order does not exist");

        orderEntity.setOrderStatus(OrderStatusCodes.CANCELLED.getDesc());
        orderDao.save(orderEntity);
    }
}

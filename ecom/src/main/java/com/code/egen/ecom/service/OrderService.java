package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.OrderEntity;
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
        orderDao.save(orderEntity);
    }
}

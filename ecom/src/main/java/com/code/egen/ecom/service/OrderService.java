package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.enums.OrderStatusCodes;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Data
public class OrderService {
    private final IOrderDao orderDao;

    public OrderEntity getOrderById(Long orderId){
        return orderDao.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order does not exist"));
    }

    public void addOrder(OrderEntity orderEntity) {
        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        orderEntity.setCreatedTimeStamp(new Date());
        orderEntity.setModifiedTimeStamp(new Date());
        orderEntity.setOrderSubTotal(getSubTotal(orderEntity));
        orderEntity.setOrderTotal(getTotal(orderEntity));
        orderDao.save(orderEntity);
    }

    private Double getSubTotal(OrderEntity orderEntity) {
        return orderEntity.getOrderItemEntities().stream().map(itemEntity -> itemEntity.getPrice() * itemEntity.getQuantity()).reduce(0.0, Double::sum);
    }

    private Double getTotal(OrderEntity orderEntity){
        return getSubTotal(orderEntity) + orderEntity.getOrderTax() + orderEntity.getShippingCharges();
    }

    public void cancelOrder(Long orderId, OrderEntity orderEntity) {
        if(orderDao.findById(orderId).isEmpty())
            throw new IllegalArgumentException("Order does not exist");

        orderEntity.setOrderStatus(OrderStatusCodes.CANCELLED.getDesc());
        orderDao.save(orderEntity);
    }
}

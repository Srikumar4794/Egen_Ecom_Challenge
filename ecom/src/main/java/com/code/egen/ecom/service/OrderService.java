package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.enums.ErrorCodes;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.OrderNotFoundException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Data
public class OrderService {
    private final IOrderDao orderDao;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderEntity getOrderById(Long orderId){
        Optional<OrderEntity> orderEntity = orderDao.findById(orderId);
        if(orderEntity.isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }

        return orderEntity.get();
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
        if(orderDao.findById(orderId).isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }
        orderEntity.setOrderStatus(OrderStatusCodes.CANCELLED.getDesc());
        orderDao.save(orderEntity);
    }
}

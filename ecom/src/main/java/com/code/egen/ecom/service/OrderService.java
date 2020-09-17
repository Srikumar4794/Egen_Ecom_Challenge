package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IAddressDao;
import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.Address;
import com.code.egen.ecom.entity.Order;
import com.code.egen.ecom.enums.ErrorCodes;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.AddressNotFoundException;
import com.code.egen.ecom.exception.OrderNotFoundException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class OrderService {
    private final IOrderDao orderDao;

    private final IAddressDao addressDao;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order getOrderById(Long orderId){
        Optional<Order> orderEntity = orderDao.findById(orderId);
        if(orderEntity.isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }

        return orderEntity.get();
    }

    public Order addOrder(Order order) throws AddressNotFoundException {
        Optional<Address> addressEntity = addressDao.findById(order.getShippingAddressId());

        if(addressEntity.isEmpty()){
            logger.error(ErrorCodes.INVALID_ADDRESS.getErrorDesc());
            throw new AddressNotFoundException(ErrorCodes.INVALID_ADDRESS.getErrorDesc());
        }

        order.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        order.setCreatedTimeStamp(new Date());
        order.setModifiedTimeStamp(new Date());
        order.setOrderSubTotal(getSubTotal(order));
        order.setOrderTotal(getTotal(order));
        Order newOrder = orderDao.save(order);

        logger.info("Order with id: " + order.getOrderId() + " created for customer id:" + order.getCustomerId());
        return newOrder;
    }

    public void addBulkOrders(List<Order> orderList) throws AddressNotFoundException {
        for(Order order: orderList){
            addOrder(order);
        }
    }

    private Double getSubTotal(Order order) {
        return order.getOrderItemEntities().stream().map(itemEntity -> itemEntity.getPrice() * itemEntity.getQuantity()).reduce(0.0, Double::sum);
    }

    private Double getTotal(Order order){
        return getSubTotal(order) + order.getOrderTax() + order.getShippingCharges();
    }

    public Order cancelOrder(Long orderId, Order order) {
        if(orderDao.findById(orderId).isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }
        order.setOrderStatus(OrderStatusCodes.CANCELLED.getDesc());
        return orderDao.save(order);
    }
}

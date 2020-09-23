package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IAddressDao;
import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.entity.AddressEntity;
import com.code.egen.ecom.entity.OrderEntity;
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

    public OrderEntity getOrderById(Long orderId){
        Optional<OrderEntity> orderEntity = orderDao.findById(orderId);
        if(orderEntity.isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }

        return orderEntity.get();
    }

    public OrderEntity addOrder(OrderEntity orderEntity) throws AddressNotFoundException {
        Optional<AddressEntity> addressEntity = addressDao.findById(orderEntity.getShippingAddressId());

        if(addressEntity.isEmpty()){
            logger.error(ErrorCodes.INVALID_ADDRESS.getErrorDesc());
            throw new AddressNotFoundException(ErrorCodes.INVALID_ADDRESS.getErrorDesc());
        }

        orderEntity.setOrderStatus(OrderStatusCodes.CREATED.getDesc());
        orderEntity.setCreatedTimeStamp(new Date());
        orderEntity.setModifiedTimeStamp(new Date());
        orderEntity.setOrderSubTotal(getSubTotal(orderEntity));
        orderEntity.setOrderTotal(getTotal(orderEntity));
        OrderEntity newOrderEntity = orderDao.save(orderEntity);

        logger.info("Order with id: " + orderEntity.getOrderId() + " created for customer id:" + orderEntity.getCustomerId());
        return newOrderEntity;
    }

    public void addBulkOrders(List<OrderEntity> orderEntityList) throws AddressNotFoundException {
/*        for(OrderEntity order: orderEntityList){
            addOrder(order);
        }*/
        orderEntityList.stream().forEach((orderEntity -> {
            if(addressDao.findById(orderEntity.getShippingAddressId()).isEmpty())
                try {
                    throw new AddressNotFoundException(ErrorCodes.INVALID_ADDRESS.getErrorDesc());
                } catch (AddressNotFoundException addressNotFoundException) {
                    addressNotFoundException.printStackTrace();
                }
        }));
    }

    private Double getSubTotal(OrderEntity orderEntity) {
        return orderEntity.getOrderItemEntities().stream().map(itemEntity -> itemEntity.getPrice() * itemEntity.getQuantity()).reduce(0.0, Double::sum);
    }

    private Double getTotal(OrderEntity orderEntity){
        return getSubTotal(orderEntity) + orderEntity.getOrderTax() + orderEntity.getShippingCharges();
    }

    public OrderEntity cancelOrder(Long orderId, OrderEntity orderEntity) {
        if(orderDao.findById(orderId).isEmpty()){
            logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
        }
        orderEntity.setOrderStatus(OrderStatusCodes.CANCELLED.getDesc());
        return orderDao.save(orderEntity);
    }
}

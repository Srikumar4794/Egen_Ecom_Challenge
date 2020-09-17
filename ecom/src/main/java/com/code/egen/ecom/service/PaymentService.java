package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IOrderDao;
import com.code.egen.ecom.dao.IPaymentDao;
import com.code.egen.ecom.dto.PaymentDTO;
import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.entity.PaymentEntity;
import com.code.egen.ecom.enums.ErrorCodes;
import com.code.egen.ecom.enums.OrderStatusCodes;
import com.code.egen.ecom.exception.OrderNotFoundException;
import com.code.egen.ecom.exception.PaymentException;
import com.code.egen.ecom.translator.IPaymentTranslator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class PaymentService {
    private final IPaymentDao paymentDao;

    private final IPaymentTranslator paymentTranslator;
    private final IOrderDao orderDao;

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentEntity addPayment(PaymentDTO paymentDTO){
        PaymentEntity paymentEntity = paymentTranslator.toPaymentEntity(paymentDTO);
        List<PaymentEntity> paymentEntities = paymentDao.findAllByOrderId(paymentEntity.getOrderId());

        if(paymentEntities != null && !paymentEntities.isEmpty()){
            Double alreadyPaid = paymentEntities.stream().map(PaymentEntity::getPaymentAmount).reduce(0.0, Double::sum);
            Optional<OrderEntity> optionalOrderEntity = orderDao.findById(paymentEntity.getOrderId());

            if(optionalOrderEntity.isEmpty()){
                logger.error(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
                throw new OrderNotFoundException(ErrorCodes.ORDER_NOT_FOUND.getErrorDesc());
            }

            double total = alreadyPaid + paymentEntity.getPaymentAmount();

            OrderEntity orderEntity = optionalOrderEntity.get();
            if(total > orderEntity.getOrderTotal()){
                logger.error(ErrorCodes.INVALID_PAYMENT_DETAILS.getErrorDesc());
                throw new PaymentException(ErrorCodes.INVALID_PAYMENT_DETAILS.getErrorDesc());
            }

            if(total == orderEntity.getOrderTotal()){
                orderEntity.setOrderStatus(OrderStatusCodes.PAID.getDesc());
                orderDao.save(orderEntity);
            }
        }
        paymentEntity.setPaymentTimeStamp(new Date());
        return paymentDao.save(paymentEntity);
    }
}

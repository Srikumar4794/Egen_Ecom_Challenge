package com.code.egen.ecom.translator;

import com.code.egen.ecom.dto.PaymentDTO;
import com.code.egen.ecom.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPaymentTranslator {

    Payment toPaymentEntity(PaymentDTO paymentDTO);
}

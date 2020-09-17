package com.code.egen.ecom.translator;

import com.code.egen.ecom.entity.Order;
import com.code.egen.ecom.vo.OrderVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderTranslator {
    OrderVO toOrderVO(Order order);
}

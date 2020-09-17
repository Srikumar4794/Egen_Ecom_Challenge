package com.code.egen.ecom.translator;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.vo.OrderVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderTranslator {
    OrderVO toOrderVO(OrderEntity orderEntity);
}

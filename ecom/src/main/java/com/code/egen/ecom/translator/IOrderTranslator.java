package com.code.egen.ecom.translator;

import com.code.egen.ecom.entity.OrderEntity;
import com.code.egen.ecom.vo.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderTranslator {

    @Mapping(target = "shippingAddress", expression = "java(orderEntity.getShipAddressEntity().getAddressLine1()." +
            "concat(" +
            " ',' + orderEntity.getShipAddressEntity().getAddressLine2()" +
            " + ',' + orderEntity.getShipAddressEntity().getCity()" +
            " + ',' + orderEntity.getShipAddressEntity().getState()" +
            " + ',' + orderEntity.getShipAddressEntity().getZipCode())" +
            ")")
    OrderVO toOrderVO(OrderEntity orderEntity);
}

package com.code.egen.ecom.dto;

import com.code.egen.ecom.entity.OrderEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BatchOrderDTO implements Serializable {
    List<OrderEntity> orderEntityList;
}

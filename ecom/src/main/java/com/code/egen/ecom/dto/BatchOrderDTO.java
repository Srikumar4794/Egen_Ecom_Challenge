package com.code.egen.ecom.dto;

import com.code.egen.ecom.entity.OrderEntity;
import lombok.Data;

import java.util.List;

@Data
public class BatchOrderDTO {
    List<OrderEntity> orderEntityList;
}

package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.AddressEntity;
import com.code.egen.ecom.service.AddressService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class AddressController extends BaseRestController{
    private final AddressService addressService;

    @PostMapping(value = "/address")
    public void addAddress(@RequestBody AddressEntity addressEntity){
        addressService.addAddress(addressEntity);
    }
}

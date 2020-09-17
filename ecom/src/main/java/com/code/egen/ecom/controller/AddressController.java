package com.code.egen.ecom.controller;

import com.code.egen.ecom.entity.Address;
import com.code.egen.ecom.service.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class AddressController {
    private final AddressService addressService;

    @PostMapping(value = "/api/v1/address")
    @ApiOperation(value = "Add a new address of a customer.")
    @ApiResponse(code = 200, message = "Added new customer address successfully.")
    public void addAddress(@RequestBody Address address){
        addressService.addAddress(address);
    }
}

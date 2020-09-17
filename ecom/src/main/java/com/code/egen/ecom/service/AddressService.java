package com.code.egen.ecom.service;

import com.code.egen.ecom.dao.IAddressDao;
import com.code.egen.ecom.entity.Address;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AddressService {
    private final IAddressDao addressDao;

    public void addAddress(Address address){
        addressDao.save(address);
    }
}

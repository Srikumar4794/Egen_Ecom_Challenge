package com.code.egen.ecom.dao;

import com.code.egen.ecom.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressDao extends JpaRepository<AddressEntity, Long> {

}

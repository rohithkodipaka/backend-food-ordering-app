package com.rohith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}

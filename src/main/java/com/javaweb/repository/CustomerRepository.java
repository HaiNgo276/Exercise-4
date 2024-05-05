package com.javaweb.repository;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import com.javaweb.repository.custom.impl.CustomerRepositoryImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerRepositoryCustom {
    List<CustomerEntity> getAllCustomers(Pageable pageable);

    int countTotalItem();
}

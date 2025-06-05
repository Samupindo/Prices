package com.develop.prices.repository;

import com.develop.prices.entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository
    extends JpaRepository<CustomerModel, Integer>, JpaSpecificationExecutor<CustomerModel> {}

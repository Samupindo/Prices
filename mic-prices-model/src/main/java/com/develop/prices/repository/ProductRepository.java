package com.develop.prices.repository;

import com.develop.prices.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository
    extends JpaRepository<ProductModel, Integer>, JpaSpecificationExecutor<ProductModel> {}

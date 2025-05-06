package com.develop.prices.repository;

import com.develop.prices.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


// JpaRepository nos da todos los métodos básicos de persistencia
public interface ProductRepository extends JpaRepository<ProductModel, Integer>, JpaSpecificationExecutor<ProductModel> {
}

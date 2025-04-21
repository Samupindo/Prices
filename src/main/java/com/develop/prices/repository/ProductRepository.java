package com.develop.prices.repository;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// JpaRepository nos da todos los métodos básicos de persistencia
public interface ProductRepository extends JpaRepository<ProductModel, Integer>, JpaSpecificationExecutor<ProductModel> {
}

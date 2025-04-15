package com.develop.prices.repository;

import com.develop.prices.modelo.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
// JpaRepository nos da todos los métodos básicos de persistencia
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    Optional<ProductModel> findByName(String name);
    List<ProductModel> findAll();



}

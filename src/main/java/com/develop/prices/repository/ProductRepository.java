package com.develop.prices.repository;

import com.develop.prices.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository nos da todos los métodos básicos de persistencia
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    Optional<ProductModel> findByName(String name);
}

package com.develop.prices.repository;

import com.develop.prices.modelo.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopLocationRepository extends JpaRepository <ShopModel, Integer> {
    @Override
    List findAll();

    ShopModel save(ShopModel entity);
}

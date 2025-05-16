package com.develop.prices.repository;

import com.develop.prices.entity.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopLocationRepository extends JpaRepository<ShopModel, Integer>, JpaSpecificationExecutor<ShopModel> {
    @Override
    ShopModel save(ShopModel entity);


}

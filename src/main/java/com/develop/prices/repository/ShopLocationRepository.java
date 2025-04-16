package com.develop.prices.repository;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopLocationRepository extends JpaRepository <ShopModel, Integer> {
    @Override
    ShopModel save(ShopModel entity);

    @Query("select s from ShopModel s " +
            "where LOWER(s.country) LIKE " +
            "LOWER(CONCAT('%', :country, '%')) or " +
            "LOWER(s.city) LIKE LOWER(CONCAT('%', :city, '%')) or " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<ShopModel> findByLocationContaining (String country, String city, String address);
}

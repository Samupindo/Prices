package com.develop.prices.repository;

import com.develop.prices.modelo.ProductPriceModel;
import com.develop.prices.modelo.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopLocationRepository extends JpaRepository<ShopModel,Integer> {

    Optional<ShopModel> findByName(String name);
    Optional<ShopModel> findByCountryCityAndAddress(String country, String city, String address);

}

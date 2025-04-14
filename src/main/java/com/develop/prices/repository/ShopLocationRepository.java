package com.develop.prices.repository;

import com.develop.prices.modelo.ProductPriceModel;
import com.develop.prices.modelo.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShopLocationRepository extends JpaRepository<ShopModel,Integer> {

    Optional<ShopModel> findByName(String name);
    Optional<ShopModel> findByCountryCityAndAddress(String country, String city, String address);

}

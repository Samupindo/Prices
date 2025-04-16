package com.develop.prices.repository;

import com.develop.prices.model.ProductPriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer> {
    Optional<ProductPriceModel> findByPrice(BigDecimal precio);
    Optional<ProductPriceModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);

    @Query("select p from ProductPriceModel p join fetch p.product prod where LOWER(prod.name) LIKE LOWER(CONCAT('%', :name, '%')) or p.price >= :priceMin or p.price < :priceMax")
    List<ProductPriceModel> foo(String name, BigDecimal priceMin,BigDecimal priceMax);
}
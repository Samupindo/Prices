package com.develop.prices.repository;

import com.develop.prices.model.ProductPriceModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.util.Optional;


public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer> {
    Optional<ProductPriceModel> findByPrice(BigDecimal precio);
    Optional<ProductPriceModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);

}

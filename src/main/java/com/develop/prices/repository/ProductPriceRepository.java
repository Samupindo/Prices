package com.develop.prices.repository;

import com.develop.prices.modelo.ProductPriceModel;
import com.develop.prices.modelo.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer> {
    Optional<ProductPriceModel> findByPrice(BigDecimal precio);
    Optional<ProductPriceModel> findByShopIdAndProductId(Integer productId, Integer shopId);

}

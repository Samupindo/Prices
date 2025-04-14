package com.develop.prices.repository;

import com.develop.prices.modelo.ProductPriceModel;
import com.develop.prices.modelo.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer> {
    Optional<ProductPriceModel> findByPrice(BigDecimal precio);
    Optional<ProductPriceModel> findByShopIdAndProductId(Integer productId, Integer shopId);

}

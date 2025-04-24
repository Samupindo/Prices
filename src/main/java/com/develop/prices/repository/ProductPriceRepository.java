package com.develop.prices.repository;

import com.develop.prices.model.ProductPriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;


public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer>, JpaSpecificationExecutor<ProductPriceModel> {
    Optional<ProductPriceModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);



}

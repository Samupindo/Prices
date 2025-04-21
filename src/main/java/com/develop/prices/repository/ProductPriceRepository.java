package com.develop.prices.repository;

import com.develop.prices.model.ProductPriceModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductPriceRepository extends JpaRepository<ProductPriceModel,Integer>, JpaSpecificationExecutor {
    Optional<ProductPriceModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);



}

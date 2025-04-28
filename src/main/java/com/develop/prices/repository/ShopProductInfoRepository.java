package com.develop.prices.repository;

import com.develop.prices.model.ShopProductInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;


public interface ShopProductInfoRepository extends JpaRepository<ShopProductInfoModel,Integer>, JpaSpecificationExecutor<ShopProductInfoModel> {
    Optional<ShopProductInfoModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);



}

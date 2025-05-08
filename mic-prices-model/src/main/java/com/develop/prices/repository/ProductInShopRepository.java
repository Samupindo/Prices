package com.develop.prices.repository;

import com.develop.prices.entity.ProductInShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface ProductInShopRepository extends JpaRepository<ProductInShopModel, Integer>, JpaSpecificationExecutor<ProductInShopModel> {
    Optional<ProductInShopModel> findByShop_ShopIdAndProduct_ProductId(Integer shopId, Integer productId);


}

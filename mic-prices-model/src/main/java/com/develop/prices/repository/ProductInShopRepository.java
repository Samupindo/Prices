package com.develop.prices.repository;

import com.develop.prices.entity.ProductInShopModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductInShopRepository
    extends JpaRepository<ProductInShopModel, Integer>,
        JpaSpecificationExecutor<ProductInShopModel> {
  Optional<ProductInShopModel> findByShop_ShopIdAndProduct_ProductId(
      Integer shopId, Integer productId);
}

package com.develop.prices.service;

import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import org.springframework.data.domain.Pageable;


public interface ShopService {

  PageResponseTo<ShopTo> findAllShopWithFilters(
      String country, String city, String address, Pageable pageable);

  ShopTo findShopById(Integer shopId);

  ShopTo saveShop(ShopAddTo shopAddTo);

  ShopTo updateShop(Integer shopId, ShopPutTo shopPutTo);

  ShopTo partialUpdateShop(Integer shopId, UpdateShopTo updateShopTo);

  void deleteShop(Integer shopId);

  ProductInShopTo addProductToShop(
      Integer productId, Integer shopId, AddProductShopTo addProductShopTo);

  ProductInShopTo updateProductPriceInShop(
      Integer shopId, Integer productId, ProductInShopPatchTo productInShopPatchTo);

  void deleteProductFromShop(Integer shopId, Integer productId);
}

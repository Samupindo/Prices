package com.develop.prices.service;

import com.develop.prices.to.*;

import java.util.List;

public interface ShopService {

    List<ShopTo> findAllShops();

    List<ShopTo> findAllShopWithFilters(String country, String city, String address);

    ShopTo findShopById(Integer shopId);

    ShopTo saveShop(ShopAddTo shopAddTo);

    ShopTo updateShop(Integer shopId, UpdateShopTo updateShopTo);

    ShopTo partialUpdateShop(Integer shopId, UpdateShopTo updateShopTo);

    void deleteShop(Integer shopId);

    ProductInShopTo addProductToShop(Integer productId, Integer shopId, AddProductShopTo addProductShopTo);

    ProductInShopTo updateProductPriceInShop(Integer shopId, Integer productId, ProductInShopPatchTo productInShopPatchTo);

    void deleteProductFromShop(Integer shopId, Integer productId);


}

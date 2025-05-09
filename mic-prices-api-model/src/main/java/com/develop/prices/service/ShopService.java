package com.develop.prices.service;

import com.develop.prices.to.ShopTo;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.UpdateShopTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.ProductInShopPatchTo;
import org.springframework.data.domain.Pageable;


public interface ShopService {

    PageResponse<ShopTo> findAllShopWithFilters(String country, String city, String address, Pageable pageable);

    ShopTo findShopById(Integer shopId);

    ShopTo saveShop(ShopAddTo shopAddTo);

    ShopTo updateShop(Integer shopId, UpdateShopTo updateShopTo);

    ShopTo partialUpdateShop(Integer shopId, UpdateShopTo updateShopTo);

    void deleteShop(Integer shopId);

    ProductInShopTo addProductToShop(Integer productId, Integer shopId, AddProductShopTo addProductShopTo);

    ProductInShopTo updateProductPriceInShop(Integer shopId, Integer productId, ProductInShopPatchTo productInShopPatchTo);

    void deleteProductFromShop(Integer shopId, Integer productId);


}

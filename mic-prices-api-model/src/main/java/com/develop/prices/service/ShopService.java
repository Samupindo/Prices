package com.develop.prices.service;

import com.develop.prices.dto.*;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    List<ShopDTO> findAllShops();

    List<ShopDTO> findAllShopWithFilters(String country, String city, String address);

    Optional<ShopDTO> findShopById(Integer shopId);

    ShopDTO saveShop(ShopAddDTO shopAddDTO);

    Optional<ShopDTO> updateShop(Integer shopId, UpdateShopDTO updateShopDTO);

    Optional<ShopDTO> partialUpdateShop(Integer shopId, UpdateShopDTO updateShopDTO);

    void deleteShop(Integer shopId);

    Optional<ProductInShopDTO> addProductToShop(Integer productId, Integer shopId, AddProductShopDTO addProductShopDTO);

    Optional<ProductInShopDTO> updateProductPriceInShop(Integer shopId, Integer productId, ProductInShopPatchDTO productInShopPatchDTO);

    void deleteProductFromShop(Integer shopId, Integer productId);


}

package com.develop.prices.mapper;

import com.develop.prices.entity.PurchaseLineModel;
import com.develop.prices.to.ProductInShopTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseLineModelMapper {

    @Mapping(target = "productInShopId", source = "productInShop.productInShopId")
    @Mapping(target = "productId", source = "productInShop.product.productId")
    @Mapping(target = "shopId", source = "productInShop.shop.shopId")
    @Mapping(target = "price", source = "productInShop.price")
    ProductInShopTo toProductInShopTo(PurchaseLineModel purchaseLineModel);


}

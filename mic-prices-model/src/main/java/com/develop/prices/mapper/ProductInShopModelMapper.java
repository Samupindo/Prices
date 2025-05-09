package com.develop.prices.mapper;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.to.ProductInShopTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductInShopModelMapper {
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "shopId", source = "shop.shopId")
    ProductInShopTo toProductInShopTo(ProductInShopModel productInShopModel);

    @Mapping(target = "productInShopId", source = "productInShopId")
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "shopId", source = "shop.shopId")
    @Mapping(target = "price", source = "price")
    ProductInShopTo toPurchaseTo(ProductInShopModel productInShopModel);
}

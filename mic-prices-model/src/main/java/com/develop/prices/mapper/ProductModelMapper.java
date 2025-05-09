package com.develop.prices.mapper;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.to.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductModelMapper {

    ProductTo toProductTo(ProductModel productModel);


    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModel(ProductNameTo productNameTo);


    @Mapping(target = "shopId", source = "shop.shopId")
    @Mapping(target = "price", source = "price")
    ShopInfoTo toShopInfoTo(ProductInShopModel productInShopModel);


}

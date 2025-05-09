package com.develop.prices.mapper;

import com.develop.prices.entity.ShopModel;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopInfoTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ShopModelMapper {

    ShopTo toShopTo(ShopModel shopModel);

    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "city", ignore = true)
    ShopModel toShopModel(ShopAddTo shopAddTo);


    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "productInShopId", ignore = true)
    @Mapping(target = "price", ignore = true)
    ShopInfoTo toShopInfoTo(ShopModel shopModel);

}

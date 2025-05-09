package com.develop.prices.mapper;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ShopModel;
import com.develop.prices.to.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopModelMapper {

    ShopTo toShopTo(ShopModel shopModel);

    PageResponse<ShopTo> toShopTos(PageResponse<ShopModel> shopModels);

    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "city", ignore = true)
    ShopModel toShopModel(ShopTo shopTo);

    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "city", ignore = true)
    ShopModel toShopModel(ShopAddTo shopAddTo);

    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "city", ignore = true)
    ShopModel toShopModelUpdate(UpdateShopTo updateShopTo);

    @Mapping(target = "shopId", ignore = true)
    @Mapping(target = "productInShopId", ignore = true)
    @Mapping(target = "price", ignore = true)
    ShopInfoTo toShopInfoTo(ShopModel shopModel);

    List<ShopInfoTo> toShopInfoTos(List<ShopModel> shopModels);

    default ShopModel map(Integer shopId) {
        if (shopId == null) {
            return null;
        }
        ShopModel shopModel = new ShopModel();
        shopModel.setShopId(shopId);
        return shopModel;
    }
}

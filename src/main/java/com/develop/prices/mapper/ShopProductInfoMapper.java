package com.develop.prices.mapper;

import com.develop.prices.dto.ShopProductInfoDTO;
import com.develop.prices.model.ShopProductInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopProductInfoMapper {
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "shopId", source = "shop.shopId")
    ShopProductInfoDTO shopProductInfoModelToShopProductInfoDTO(ShopProductInfoModel shopProductInfoModel);


}

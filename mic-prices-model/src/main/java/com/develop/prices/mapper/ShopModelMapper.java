package com.develop.prices.mapper;

import com.develop.prices.dto.ShopDTO;
import com.develop.prices.entity.ShopModel;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopModelMapper {
    ShopDTO shopModelToShopDTO(ShopModel shopModel);

    ShopModel shopDTOToShopModel(ShopDTO shopDTO);
}

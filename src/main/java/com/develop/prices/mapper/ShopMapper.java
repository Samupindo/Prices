package com.develop.prices.mapper;

import com.develop.prices.dto.ShopDTO;
import com.develop.prices.model.ShopModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopDTO shopModelToShopDTO(ShopModel shopModel);

    ShopModel shopDTOToShopModel(ShopDTO shopDTO);
}

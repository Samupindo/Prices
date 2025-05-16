package com.develop.prices.mapper;

import com.develop.prices.dto.ShopAddDto;
import com.develop.prices.dto.ShopDto;
import com.develop.prices.dto.ShopPutDto;
import com.develop.prices.dto.UpdateShopDto;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopRestMapper {
  ShopDto toShopDto(ShopTo shopTo);

  ShopAddTo toShopAddTo(ShopAddDto shopAddDto);

  UpdateShopTo toUpdateShopTo(UpdateShopDto updateShopDto);

  ShopPutTo toShopPutTo(ShopPutDto shopPutDto);
}

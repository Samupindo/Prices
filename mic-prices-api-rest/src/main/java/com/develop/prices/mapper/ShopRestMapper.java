package com.develop.prices.mapper;

import com.develop.prices.dto.ShopAddDTO;
import com.develop.prices.dto.ShopDTO;
import com.develop.prices.dto.ShopPutDTO;
import com.develop.prices.dto.UpdateShopDTO;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopRestMapper {
  ShopDTO toShopDTO(ShopTo shopTo);

  ShopAddTo toShopAddTo(ShopAddDTO shopAddDTO);

  UpdateShopTo toUpdateShopTo(UpdateShopDTO updateShopDTO);

  ShopPutTo toShopPutTo(ShopPutDTO shopPutDTO);
}

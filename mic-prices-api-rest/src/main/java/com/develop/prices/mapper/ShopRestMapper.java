package com.develop.prices.mapper;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.ShopAddDTO;
import com.develop.prices.dto.ShopDTO;
import com.develop.prices.dto.UpdateShopDTO;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopRestMapper {
    ShopDTO toShopDTO(ShopTo shopTo);

    List<ShopDTO> toListShopDTO(List<ShopTo> shopTo);

//    List<ShopDTO> toListShopDTO(PageResponse<ShopTo> shopTo);

    ShopAddTo toShopAddTo(ShopAddDTO shopAddDTO);

    UpdateShopTo toUpdateShopTo(UpdateShopDTO updateShopDTO);
}

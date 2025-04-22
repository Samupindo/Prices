package com.develop.prices.mapper;

import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.dto.ProductPriceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPriceMapper {
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "shopId", source = "shop.shopId")
    ProductPriceDTO productPriceModelToProductPriceDTO(ProductPriceModel productPriceModel);

    ProductPriceModel productPriceDTOToproductPriceModel(ProductPriceDTO productPriceDTO);
}

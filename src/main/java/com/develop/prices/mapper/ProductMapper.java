package com.develop.prices.mapper;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ShopModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO (ProductModel productModel);

}

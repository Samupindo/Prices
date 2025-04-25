package com.develop.prices.mapper;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.model.ProductModel;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO (ProductModel productModel);

}

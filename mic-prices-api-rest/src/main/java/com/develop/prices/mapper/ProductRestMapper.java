package com.develop.prices.mapper;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductWithShopsTo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    ProductDTO toProductDTO(ProductTo productTo);

    List<ProductDTO> toListProductDTO(List<ProductTo> productTo);

    List<ProductDTO> toListProductDTO(PageResponse<ProductTo> productTo);

    ProductNameTo toProductNameTo(ProductNameDTO productNameDTO);

    ProductWithShopsTo toProductWithShopsTo(ProductWithShopsDTO productWithShopsDTO);
}

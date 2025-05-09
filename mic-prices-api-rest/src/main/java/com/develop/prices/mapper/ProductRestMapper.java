package com.develop.prices.mapper;

import com.develop.prices.dto.*;
import com.develop.prices.to.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    ProductDTO toProductDTO(ProductTo productTo);

    List<ProductDTO> toListProductDTO(List<ProductTo> productTo);

    ProductNameTo toProductNameTo(ProductNameDTO productNameDTO);

    ProductWithShopsTo toProductWithShopsTo(ProductWithShopsDTO productWithShopsDTO);

    ProductWithShopsDTO toProductWithShopsDTO(ProductWithShopsTo productWithShopsTo);

    ProductInShopPatchTo toProductInShopPatchTo(ProductInShopPatchDTO productInShopPatchDTO);

    ProductInShopDTO toProductInShopDTO(ProductInShopTo productInShopTo);

    AddProductShopTo toAddProductShopTo(AddProductShopDTO addProductShopDTO);
}

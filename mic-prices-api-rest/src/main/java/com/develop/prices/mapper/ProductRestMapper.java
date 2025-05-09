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

    ProductNameTo toProductNameTo(ProductNameDTO productNameDTO);


    ProductWithShopsDTO toProductWithShopsDTO(ProductWithShopsTo productWithShopsTo);

    ProductInShopPatchTo toProductInShopPatchTo(ProductInShopPatchDTO productInShopPatchDTO);

    ProductInShopDTO toProductInShopDTO(ProductInShopTo productInShopTo);

    AddProductShopTo toAddProductShopTo(AddProductShopDTO addProductShopDTO);
}

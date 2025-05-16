package com.develop.prices.mapper;

import com.develop.prices.dto.AddProductShopDTO;
import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductInShopDTO;
import com.develop.prices.dto.ProductInShopPatchDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

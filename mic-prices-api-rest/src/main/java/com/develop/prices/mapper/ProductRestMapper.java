package com.develop.prices.mapper;

import com.develop.prices.dto.AddProductShopDto;
import com.develop.prices.dto.ProductDto;
import com.develop.prices.dto.ProductInShopDto;
import com.develop.prices.dto.ProductInShopPatchDto;
import com.develop.prices.dto.ProductNameDto;
import com.develop.prices.dto.ProductWithShopsDto;
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
  ProductDto toProductDto(ProductTo productTo);

  ProductNameTo toProductNameTo(ProductNameDto productNameDto);

  ProductWithShopsDto toProductWithShopsDto(ProductWithShopsTo productWithShopsTo);

  ProductInShopPatchTo toProductInShopPatchTo(ProductInShopPatchDto productInShopPatchDto);

  ProductInShopDto toProductInShopDto(ProductInShopTo productInShopTo);

  AddProductShopTo toAddProductShopTo(AddProductShopDto addProductShopDto);
}

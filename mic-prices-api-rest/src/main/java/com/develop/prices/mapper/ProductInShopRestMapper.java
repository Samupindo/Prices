package com.develop.prices.mapper;

import com.develop.prices.dto.ProductInShopDto;
import com.develop.prices.to.ProductInShopTo;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInShopRestMapper {

  ProductInShopDto toProductInShopDto(ProductInShopTo productInShopTo);

  List<ProductInShopDto> toListCustomerDto(List<ProductInShopTo> productInShopTo);
}

package com.develop.prices.mapper;

import com.develop.prices.dto.ProductInShopDTO;
import com.develop.prices.to.ProductInShopTo;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInShopRestMapper {

    ProductInShopDTO toProductInShopDTO(ProductInShopTo productInShopTo);

    List<ProductInShopDTO> toListCustomerDTO(List<ProductInShopTo> productInShopTo);

}

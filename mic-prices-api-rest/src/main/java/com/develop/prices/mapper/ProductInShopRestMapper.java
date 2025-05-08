package com.develop.prices.mapper;

import com.develop.prices.dto.ProductInShopDTO;
import com.develop.prices.dto.ProductInShopPatchDTO;
import com.develop.prices.dto.ShopInfoDTO;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ShopInfoTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductInShopRestMapper {

    ProductInShopDTO toProductInShopDTO(ProductInShopTo productInShopTo);

    List<ProductInShopDTO> toListCustomerDTO(List<ProductInShopTo> productInShopTo);

//    List<ProductInShopDTO> toListCustomerDTO(PageResponse<ProductInShopTo> productInShopTo);

    ProductInShopPatchTo toProductInShopPatchTo(ProductInShopPatchDTO productInShopPatchDTO);

    ShopInfoTo toShopInfoTo(ShopInfoDTO shopInfoDTO);
}

package com.develop.prices.mapper;

import com.develop.prices.entity.PurchaseLineModel;
import com.develop.prices.to.ProductInShopTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseLineModelMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "productInShopId", source = "productInShopId")
    @Mapping(target = "shopId", source = "shopId")
    @Mapping(target = "price", source = "productInShop.price")
    ProductInShopTo toProductInShopTo(PurchaseLineModel purchaseLineModel);

    List<ProductInShopTo> toProductInShopTos(List<PurchaseLineModel> purchaseLineModels);

}

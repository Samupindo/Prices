package com.develop.prices.mapper;

import com.develop.prices.entity.PurchaseLineModel;
import com.develop.prices.to.ProductInShopTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseLineModelMapper {
    @Mapping(target = "productId", source = "productInShop.product.productId")
    @Mapping(target = "price", source = "productInShop.price")
    ProductInShopTo toProductInShopTo(PurchaseLineModel purchaseLineModel);

    List<ProductInShopTo> toProductInShopTos(List<PurchaseLineModel> purchaseLineModels);

}

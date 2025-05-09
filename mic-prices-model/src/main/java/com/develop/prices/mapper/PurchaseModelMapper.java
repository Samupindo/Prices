package com.develop.prices.mapper;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.PurchaseModel;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseModelMapper {

    @Mapping(target = "products", source = "purchaseLineModels")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "shopping", source = "shopping")
    PurchaseTo toPurchaseTo(PurchaseModel purchaseModel);


}

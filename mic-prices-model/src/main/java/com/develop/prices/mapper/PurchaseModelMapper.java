package com.develop.prices.mapper;


import com.develop.prices.entity.PurchaseModel;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PurchaseModelMapper {

    @Mapping(target = "products", source = "purchaseLineModels")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "shopping", source = "shopping")
    PurchaseTo toPurchaseTo(PurchaseModel purchaseModel);


}

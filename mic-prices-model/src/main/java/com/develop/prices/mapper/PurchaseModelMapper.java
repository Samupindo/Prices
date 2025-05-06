package com.develop.prices.mapper;

import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.entity.PurchaseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ProductInShopModelMapper.class})
public interface PurchaseModelMapper {
    PurchaseDTO purchaseModelToPurchaseDTO (PurchaseModel purchaseModel);

}

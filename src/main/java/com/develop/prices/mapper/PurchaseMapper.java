package com.develop.prices.mapper;

import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.model.PurchaseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ProductInShopMapper.class})
public interface PurchaseMapper {
    PurchaseDTO purchaseModelToPurchaseDTO (PurchaseModel purchaseModel);

}

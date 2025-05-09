package com.develop.prices.mapper;

import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductInShopRestMapper.class})
public interface PurchaseRestMapper {

    PurchaseDTO toPurchaseDTO(PurchaseTo purchaseTo);


    @Mapping(target = "customerId", source = "customerId")
    PostPurchaseTo toPostPurchaseTo(PostPurchaseDTO postPurchaseDTO);
}

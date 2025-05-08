package com.develop.prices.mapper;

import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductInShopRestMapper.class})
public interface PurchaseRestMapper {

    @Mapping(target = "customerId", source = "customer.customerId")
    PurchaseDTO toPurchaseDTO(PurchaseTo purchaseTo);

    List<PurchaseDTO> toListPurchaseDTO(List<PurchaseTo> purchaseTo);

    @Mapping(target = "customerId", source = "customer.customerId")
    PostPurchaseTo toPostPurchaseTo(PostPurchaseDTO postPurchaseDTO);
}

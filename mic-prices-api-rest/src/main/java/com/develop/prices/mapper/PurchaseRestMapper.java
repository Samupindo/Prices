package com.develop.prices.mapper;

import com.develop.prices.dto.PostPurchaseDto;
import com.develop.prices.dto.PurchaseDto;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ProductInShopRestMapper.class})
public interface PurchaseRestMapper {

  PurchaseDto toPurchaseDto(PurchaseTo purchaseTo);

  @Mapping(target = "customerId", source = "customerId")
  PostPurchaseTo toPostPurchaseTo(PostPurchaseDto postPurchaseDto);
}

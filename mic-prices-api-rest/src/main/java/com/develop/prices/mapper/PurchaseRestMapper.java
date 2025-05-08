package com.develop.prices.mapper;

import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductInShopRestMapper.class})
public interface PurchaseRestMapper {

    PurchaseDTO toPurchaseDTO(PurchaseTo purchaseTo);

    List<PurchaseDTO> toListPurchaseDTO(List<PurchaseTo> purchaseTo);

//    List<PurchaseDTO> toListPurchaseDTO(PageResponse<PurchaseTo> purchaseTo);

    PostPurchaseTo toPostPurchaseTo(PostPurchaseDTO postPurchaseDTO);
}

package com.develop.prices.mapper;

import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.dto.PurchaseProductDTO;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.PurchaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ProductPriceMapper.class})
public interface PurchaseMapper {
    PurchaseDTO purchaseModelToPurchaseDTO (PurchaseModel purchaseModel);

}

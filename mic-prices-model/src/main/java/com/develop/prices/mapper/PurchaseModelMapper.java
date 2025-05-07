package com.develop.prices.mapper;

import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.entity.CustomerModel;
import com.develop.prices.entity.PurchaseModel;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProductInShopModelMapper.class})
public interface PurchaseModelMapper {
    @Mapping(source = "customer.customerId", target = "customerId")
    PurchaseTo toPurchaseTo(PurchaseModel purchaseModel);

    List<PurchaseTo> toPurchaseTo(List<PurchaseModel> purchaseModels);

    @Mapping(target = "purchaseId", ignore = true)
    @Mapping(target = "purchaseLineModels", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    PurchaseModel toPurchaseModel(PostPurchaseTo postPurchaseTo);

    default PurchaseModel map(Integer purchaseId) {
        if (purchaseId == null) return null;
        PurchaseModel purchase = new PurchaseModel();
        purchase.setPurchaseId(purchaseId);
        return purchase;
    }

    default CustomerModel mapCustomer(Integer customerId) {
        if (customerId == null) return null;
        CustomerModel customer = new CustomerModel();
        customer.setCustomerId(customerId);
        return customer;
    }
}

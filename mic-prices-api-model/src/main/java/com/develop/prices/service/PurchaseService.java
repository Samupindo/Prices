package com.develop.prices.service;

import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    List<PurchaseTo> findAllPurchase();

    List<PurchaseTo> findAllWithFilters(Integer customerId, List<Integer> productInShop,
                                        BigDecimal totalPriceMax, BigDecimal totalPriceMin,
                                        Boolean shopping, Pageable pageable);

    Optional<PurchaseTo> findPurchaseById(Integer purchaseId);

    PurchaseTo savePurchase(PostPurchaseTo postPurchaseTo);

    PurchaseTo savePurchaseAndPurchaseLine(Integer purchaseId, Integer productInShopId);

    PurchaseTo updatePurchaseStatusToFinishes(Integer purchaseId);

    void deletePurchase(Integer purchaseId);

    void deleteProductToPurchase(Integer purchaseId, Integer productInShopId);
}

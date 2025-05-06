package com.develop.prices.service;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    List<PurchaseDTO> findAllPurchase();

    PageResponse<PurchaseDTO> findAllWithFilters(Integer customerId, List<Integer> productInShop,
                                                 BigDecimal totalPriceMax, BigDecimal totalPriceMin,
                                                 Boolean shopping, Pageable pageable);

    Optional<PurchaseDTO> findPurchaseById(Integer purchaseId);

    PurchaseDTO savePurchase(PostPurchaseDTO postPurchaseDTO);

    Optional<PurchaseDTO> savePurchaseAndPurchaseLine(Integer purchaseId, Integer productInShopId);

    Optional<PurchaseDTO> updatePurchaseStatusToFinishes(Integer purchaseId);

    void deletePurchase(Integer purchaseId);
}

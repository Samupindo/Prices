package com.develop.prices.service;

import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseService {


  PageResponseTo<PurchaseTo> findAllWithFilters(Integer customerId, List<Integer> productInShop,
                                                BigDecimal totalPriceMax, BigDecimal totalPriceMin,
                                                Boolean shopping, Pageable pageable);


  PurchaseTo findPurchaseById(Integer purchaseId);

  PurchaseTo savePurchase(PostPurchaseTo postPurchaseTo);

  PurchaseTo savePurchaseAndPurchaseLine(Integer purchaseId, Integer productInShopId);

  PurchaseTo updatePurchaseStatusToFinishes(Integer purchaseId);

  void deletePurchase(Integer purchaseId);

  void deleteProductToPurchase(Integer purchaseId, Integer productInShopId);
}

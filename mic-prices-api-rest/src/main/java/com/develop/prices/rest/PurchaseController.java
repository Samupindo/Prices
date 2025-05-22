package com.develop.prices.rest;

import com.develop.prices.controller.PurchasesApi;
import com.develop.prices.dto.PageResponseDtoPurchaseDto;
import com.develop.prices.dto.PostPurchaseDto;
import com.develop.prices.dto.PurchaseDto;
import com.develop.prices.mapper.PurchaseRestMapper;
import com.develop.prices.service.PurchaseService;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController implements PurchasesApi {

  private final PurchaseService purchaseService;

  private final PurchaseRestMapper purchaseRestMapper;

  @Autowired
  public PurchaseController(
      PurchaseService purchaseService, PurchaseRestMapper purchaseRestMapper) {
    this.purchaseService = purchaseService;
    this.purchaseRestMapper = purchaseRestMapper;
  }

  @Override
  public ResponseEntity<PageResponseDtoPurchaseDto> getPurchasesWithFilters(
      Integer customerId,
      List<Integer> productInShop,
      BigDecimal totalPriceMax,
      BigDecimal totalPriceMin,
      Boolean shopping,
      Pageable pageable) {

    pageable =
        PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "purchaseId"));

    PageResponseTo<PurchaseTo> purchaseToPageResponseTo =
        purchaseService.findAllWithFilters(
            customerId, productInShop, totalPriceMax, totalPriceMin, shopping, pageable);

    List<PurchaseDto> purchaseToList =
        purchaseToPageResponseTo.getContent().stream()
            .map(purchaseRestMapper::toPurchaseDto)
            .toList();

    PageResponseDtoPurchaseDto response = new PageResponseDtoPurchaseDto();
    response.setContent(purchaseToList);
    response.setTotalElements(purchaseToPageResponseTo.getTotalElements());
    response.setTotalPages(purchaseToPageResponseTo.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<PurchaseDto> getPurchaseById(Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.findPurchaseById(purchaseId);

    return ResponseEntity.ok(purchaseRestMapper.toPurchaseDto(purchaseTo));
  }

  @Override
  public ResponseEntity<PurchaseDto> postPurchase(PostPurchaseDto postPurchaseDto) {
    PostPurchaseTo postPurchaseTo = purchaseRestMapper.toPostPurchaseTo(postPurchaseDto);

    PurchaseTo purchaseTo = purchaseService.savePurchase(postPurchaseTo);

    PurchaseDto purchaseDto = purchaseRestMapper.toPurchaseDto(purchaseTo);

    return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(value = "{ \"error\": \"Missing required field: name\" }")))
      })
  @Override
  public ResponseEntity<PurchaseDto> addProductPurchase(
      Integer purchaseId, Integer productInShopId) {
    PurchaseTo purchaseTo =
        purchaseService.savePurchaseAndPurchaseLine(purchaseId, productInShopId);
    PurchaseDto purchaseDto = purchaseRestMapper.toPurchaseDto(purchaseTo);

    return ResponseEntity.ok(purchaseDto);
  }

  @Override
  public ResponseEntity<PurchaseDto> finishPurchase(Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.updatePurchaseStatusToFinishes(purchaseId);

    PurchaseDto purchaseDto = purchaseRestMapper.toPurchaseDto(purchaseTo);

    return ResponseEntity.ok(purchaseDto);
  }

  @Override
  public ResponseEntity<Void> deletePurchase(Integer purchaseId) {
    purchaseService.deletePurchase(purchaseId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteProductPurchase(Integer purchaseId, Integer productInShopId) {
    purchaseService.deleteProductToPurchase(purchaseId, productInShopId);
    return ResponseEntity.ok().build();
  }
}

package com.develop.prices.rest;

import com.develop.prices.dto.PageResponseDto;
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
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

  private final PurchaseService purchaseService;

  private final PurchaseRestMapper purchaseRestMapper;

  @Autowired
  public PurchaseController(
      PurchaseService purchaseService, PurchaseRestMapper purchaseRestMapper) {
    this.purchaseService = purchaseService;
    this.purchaseRestMapper = purchaseRestMapper;
  }

  @GetMapping("")
  public ResponseEntity<PageResponseDto<PurchaseDto>> getPurchasesWithFilters(
      @RequestParam(required = false) Integer customerId,
      @RequestParam(required = false) List<Integer> productInShop,
      @RequestParam(required = false) BigDecimal totalPriceMax,
      @RequestParam(required = false) BigDecimal totalPriceMin,
      @RequestParam(required = false) Boolean shopping,
      @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {

    PageResponseTo<PurchaseTo> purchaseToPageResponseTo =
        purchaseService.findAllWithFilters(
            customerId, productInShop, totalPriceMax, totalPriceMin, shopping, pageable);

    List<PurchaseDto> purchaseToList =
        purchaseToPageResponseTo.getContent().stream()
            .map(purchaseRestMapper::toPurchaseDto)
            .toList();

    PageResponseDto<PurchaseDto> response =
        new PageResponseDto<>(purchaseToList, purchaseToList.size(), 1);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{purchaseId}")
  public ResponseEntity<PurchaseDto> getPurchaseById(@PathVariable Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.findPurchaseById(purchaseId);

    return ResponseEntity.ok(purchaseRestMapper.toPurchaseDto(purchaseTo));
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
  @PostMapping("")
  public ResponseEntity<PurchaseDto> postPurchase(
      @Valid @RequestBody PostPurchaseDto postPurchaseDto) {
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
  @PostMapping("/{purchaseId}/productInShop/{productInShopId}")
  public ResponseEntity<PurchaseDto> addProductPurchase(
      @PathVariable Integer purchaseId, @PathVariable Integer productInShopId) {
    PurchaseTo purchaseTo =
        purchaseService.savePurchaseAndPurchaseLine(purchaseId, productInShopId);
    PurchaseDto purchaseDto = purchaseRestMapper.toPurchaseDto(purchaseTo);

    return ResponseEntity.ok(purchaseDto);
  }

  @PatchMapping("/{purchaseId}/finish")
  public ResponseEntity<PurchaseDto> finishPurchase(@PathVariable Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.updatePurchaseStatusToFinishes(purchaseId);

    PurchaseDto purchaseDto = purchaseRestMapper.toPurchaseDto(purchaseTo);

    return ResponseEntity.ok(purchaseDto);
  }

  @DeleteMapping("{purchaseId}")
  public ResponseEntity<Void> deletePurchase(@PathVariable Integer purchaseId) {
    purchaseService.deletePurchase(purchaseId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{purchaseId}/productInShop/{productInShopId}")
  public ResponseEntity<Void> deleteProductPurchase(
      @PathVariable Integer purchaseId, @PathVariable Integer productInShopId) {
    purchaseService.deleteProductToPurchase(purchaseId, productInShopId);
    return ResponseEntity.ok().build();
  }
}
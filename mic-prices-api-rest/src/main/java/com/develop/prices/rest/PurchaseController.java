package com.develop.prices.rest;

import com.develop.prices.dto.PageResponseDTO;
import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
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
  public ResponseEntity<PageResponseDTO<PurchaseDTO>> getPurchasesWithFilters(
      @RequestParam(required = false) Integer customerId,
      @RequestParam(required = false) List<Integer> productInShop,
      @RequestParam(required = false) BigDecimal totalPriceMax,
      @RequestParam(required = false) BigDecimal totalPriceMin,
      @RequestParam(required = false) Boolean shopping,
      @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {

    PageResponseTo<PurchaseTo> purchaseToPageResponseTo =
        purchaseService.findAllWithFilters(
            customerId, productInShop, totalPriceMax, totalPriceMin, shopping, pageable);

    List<PurchaseDTO> purchaseToList =
        purchaseToPageResponseTo.getContent().stream()
            .map(purchaseRestMapper::toPurchaseDTO)
            .toList();

    PageResponseDTO<PurchaseDTO> response =
        new PageResponseDTO<>(purchaseToList, purchaseToList.size(), 1);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{purchaseId}")
  public ResponseEntity<PurchaseDTO> getPurchaseById(@PathVariable Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.findPurchaseById(purchaseId);

    return ResponseEntity.ok(purchaseRestMapper.toPurchaseDTO(purchaseTo));
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
  public ResponseEntity<PurchaseDTO> postPurchase(
      @Valid @RequestBody PostPurchaseDTO postPurchaseDTO) {
    PostPurchaseTo postPurchaseTo = purchaseRestMapper.toPostPurchaseTo(postPurchaseDTO);

    PurchaseTo purchaseTo = purchaseService.savePurchase(postPurchaseTo);

    PurchaseDTO purchaseDTO = purchaseRestMapper.toPurchaseDTO(purchaseTo);

    return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDTO);
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
  public ResponseEntity<PurchaseDTO> addProductPurchase(
      @PathVariable Integer purchaseId, @PathVariable Integer productInShopId) {
    PurchaseTo purchaseTo =
        purchaseService.savePurchaseAndPurchaseLine(purchaseId, productInShopId);
    PurchaseDTO purchaseDTO = purchaseRestMapper.toPurchaseDTO(purchaseTo);

    return ResponseEntity.ok(purchaseDTO);
  }

  @PatchMapping("/{purchaseId}/finish")
  public ResponseEntity<PurchaseDTO> finishPurchase(@PathVariable Integer purchaseId) {
    PurchaseTo purchaseTo = purchaseService.updatePurchaseStatusToFinishes(purchaseId);

    PurchaseDTO purchaseDTO = purchaseRestMapper.toPurchaseDTO(purchaseTo);

    return ResponseEntity.ok(purchaseDTO);
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

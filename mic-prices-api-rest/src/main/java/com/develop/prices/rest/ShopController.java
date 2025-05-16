package com.develop.prices.rest;

import com.develop.prices.dto.AddProductShopDto;
import com.develop.prices.dto.PageResponseDto;
import com.develop.prices.dto.ProductInShopDto;
import com.develop.prices.dto.ProductInShopPatchDto;
import com.develop.prices.dto.ShopAddDto;
import com.develop.prices.dto.ShopDto;
import com.develop.prices.dto.ShopPutDto;
import com.develop.prices.dto.UpdateShopDto;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.mapper.ShopRestMapper;
import com.develop.prices.service.ShopService;
import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {
  private final ShopService shopService;
  private final ShopRestMapper shopRestMapper;
  private final ProductRestMapper productRestMapper;

  @Autowired
  public ShopController(
      ShopService shopService, ShopRestMapper shopRestMapper, ProductRestMapper productRestMapper) {
    this.shopService = shopService;
    this.shopRestMapper = shopRestMapper;
    this.productRestMapper = productRestMapper;
  }

  @GetMapping("")
  public ResponseEntity<PageResponseDto<ShopDto>> getShopLocationWithFilters(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String address,
      @PageableDefault(sort = "shopId", direction = Sort.Direction.ASC) Pageable pageable) {

    PageResponseTo<ShopTo> shopTo =
        shopService.findAllShopWithFilters(country, city, address, pageable);

    List<ShopDto> shopDtoList =
        shopTo.getContent().stream().map(shopRestMapper::toShopDto).toList();

    PageResponseDto<ShopDto> shopDtoPageResponseDto =
        new PageResponseDto<>(shopDtoList, shopTo.getTotalElements(), shopTo.getTotalPages());

    return ResponseEntity.ok(shopDtoPageResponseDto);
  }

  @GetMapping("/{shopId}")
  public ResponseEntity<ShopDto> getShopById(@PathVariable(required = false) Integer shopId) {
    ShopTo shopTo = shopService.findShopById(shopId);

    if (shopTo == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    ShopDto shopDto = shopRestMapper.toShopDto(shopTo);

    return ResponseEntity.ok(shopDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                "{ \"shopId\": 4, \"country\": \"España\", "
                                    +
                                    "\"city\": \"Coruña\", "
                                    +
                                    "\"address\": "
                                    +
                                    "\"Os Mallos 10\" }"))),
        @ApiResponse(
            responseCode = "400",
            description = "Missing or invalid fields",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(value = "{ \"error\": \"Missing "
                            +
                            "required field: city\" }"))),
        @ApiResponse(
            responseCode = "409",
            description = "Shop already exists",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                "{ \"error\": \"Shop already exists "
                                    +
                                    "at this address in this "
                                    +
                                    "city and country\" }")))
      })
  @PostMapping("")
  public ResponseEntity<ShopDto> addShop(@Valid @RequestBody ShopAddDto shopAddDto) {
    ShopAddTo shopAddTo = shopRestMapper.toShopAddTo(shopAddDto);

    ShopTo shopTo = shopService.saveShop(shopAddTo);

    ShopDto shopDto = shopRestMapper.toShopDto(shopTo);

    return ResponseEntity.status(HttpStatus.CREATED).body(shopDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product added to shop",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value = "{ \"productId\": 1, \"shopId\": 2, \"price\": 15.99 }"))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid price provided",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                "{ \"error\": \"Price must be greater than or equal to 0\" }"))),
        @ApiResponse(
            responseCode = "404",
            description = "Shop or product not found",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(value = "{ \"error\": \"Product or shop not found\" }"))),
        @ApiResponse(
            responseCode = "409",
            description = "Product already exists in shop",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                "{ \"error\": \"This product is already registered in this "
                                    +
                                    "shop\" }")))
      })
  @PostMapping("/{shopId}/products/{productId}")
  public ResponseEntity<ProductInShopDto> addProductShop(
      @PathVariable Integer productId,
      @PathVariable Integer shopId,
      @Valid @RequestBody AddProductShopDto addProductShopDto) {

    AddProductShopTo addProductShopTo = productRestMapper.toAddProductShopTo(addProductShopDto);

    ProductInShopTo productInShopTo =
        shopService.addProductToShop(productId, shopId, addProductShopTo);

    return ResponseEntity.ok(productRestMapper.toProductInShopDto(productInShopTo));
  }

  @PutMapping("/{shopId}")
  public ResponseEntity<ShopDto> updateShop(
      @PathVariable Integer shopId, @Valid @RequestBody ShopPutDto shopPutDto) {

    ShopPutTo shopPutTo = shopRestMapper.toShopPutTo(shopPutDto);

    ShopTo shopTo = shopService.updateShop(shopId, shopPutTo);

    return ResponseEntity.ok(shopRestMapper.toShopDto(shopTo));
  }

  @PatchMapping("/{shopId}")
  public ResponseEntity<ShopDto> partialUpdateShop(
      @PathVariable Integer shopId, @Valid @RequestBody UpdateShopDto updateShopDto) {

    UpdateShopTo updateShopTo = shopRestMapper.toUpdateShopTo(updateShopDto);

    ShopTo shopTo = shopService.partialUpdateShop(shopId, updateShopTo);

    return ResponseEntity.ok(shopRestMapper.toShopDto(shopTo));
  }

  @PatchMapping("/{shopId}/products/{productId}")
  public ResponseEntity<ProductInShopDto> updateProductInShop(
      @PathVariable Integer shopId,
      @PathVariable Integer productId,
      @Valid @RequestBody ProductInShopPatchDto productInShopPatchDto) {

    BigDecimal price = productInShopPatchDto.getPrice();

    ProductInShopPatchTo productInShopPatchTo =
        productRestMapper.toProductInShopPatchTo(productInShopPatchDto);

    productInShopPatchTo.setPrice(price);

    ProductInShopTo productInShopTo =
        shopService.updateProductPriceInShop(shopId, productId, productInShopPatchTo);

    return ResponseEntity.ok(productRestMapper.toProductInShopDto(productInShopTo));
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The shop has been deleted successfully"),
        @ApiResponse(
            responseCode = "404",
            description = "Shop not found",
            content =
                @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"error\": \"Shop not found\" }"))),
      })
  @DeleteMapping("/{shopId}")
  public ResponseEntity<ShopDto> deleteShop(@PathVariable Integer shopId) {
    shopService.deleteShop(shopId);

    return ResponseEntity.ok().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The shop has been deleted successfully"),
        @ApiResponse(
            responseCode = "404",
            description = "Shop not found",
            content =
                @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"error\": \"Shop not found\" }"))),
      })
  @DeleteMapping("/{shopId}/products/{productId}")
  public ResponseEntity<ProductInShopDto> deleteProductFromShop(
      @PathVariable Integer productId, @PathVariable Integer shopId) {

    shopService.deleteProductFromShop(shopId, productId);

    return ResponseEntity.ok().build();
  }
}

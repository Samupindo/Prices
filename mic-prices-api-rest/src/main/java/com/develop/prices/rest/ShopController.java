package com.develop.prices.rest;

import com.develop.prices.controller.ShopsApi;
import com.develop.prices.dto.AddProductShopDto;
import com.develop.prices.dto.PageResponseDtoShopDto;
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
public class ShopController implements ShopsApi {
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

  @Override
  public ResponseEntity<PageResponseDtoShopDto> getShopLocationWithFilters(
      String country, String city, String address, Pageable pageable) {

    pageable = PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "shopId"));

    PageResponseTo<ShopTo> shopTo =
        shopService.findAllShopWithFilters(country, city, address, pageable);

    List<ShopDto> shopDtoList =
        shopTo.getContent().stream().map(shopRestMapper::toShopDto).toList();

    PageResponseDtoShopDto response = new PageResponseDtoShopDto();
    response.setContent(shopDtoList);
    response.setTotalElements(shopTo.getTotalElements());
    response.setTotalPages(shopTo.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ShopDto> getShopById(Integer shopId) {
    ShopTo shopTo = shopService.findShopById(shopId);

    if (shopTo == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    ShopDto shopDto = shopRestMapper.toShopDto(shopTo);

    return ResponseEntity.ok(shopDto);
  }


  @Override
  public ResponseEntity<ShopDto> addShop(ShopAddDto shopAddDto) {
    ShopAddTo shopAddTo = shopRestMapper.toShopAddTo(shopAddDto);

    ShopTo shopTo = shopService.saveShop(shopAddTo);

    ShopDto shopDto = shopRestMapper.toShopDto(shopTo);

    return ResponseEntity.status(HttpStatus.CREATED).body(shopDto);
  }


  @Override
  public ResponseEntity<ProductInShopDto> addProductShop(
      Integer productId, Integer shopId, AddProductShopDto addProductShopDto) {
    AddProductShopTo addProductShopTo = productRestMapper.toAddProductShopTo(addProductShopDto);

    ProductInShopTo productInShopTo =
        shopService.addProductToShop(productId, shopId, addProductShopTo);

    return ResponseEntity.ok(productRestMapper.toProductInShopDto(productInShopTo));
  }

  @Override
  public ResponseEntity<ShopDto> updateShop(Integer shopId, ShopPutDto shopPutDto) {
    ShopPutTo shopPutTo = shopRestMapper.toShopPutTo(shopPutDto);

    ShopTo shopTo = shopService.updateShop(shopId, shopPutTo);

    return ResponseEntity.ok(shopRestMapper.toShopDto(shopTo));
  }

  @Override
  public ResponseEntity<ProductInShopDto> updateProductInShop(
      Integer shopId, Integer productId, ProductInShopPatchDto productInShopPatchDto) {
    BigDecimal price = productInShopPatchDto.getPrice();

    ProductInShopPatchTo productInShopPatchTo =
        productRestMapper.toProductInShopPatchTo(productInShopPatchDto);

    productInShopPatchTo.setPrice(price);

    ProductInShopTo productInShopTo =
        shopService.updateProductPriceInShop(shopId, productId, productInShopPatchTo);

    return ResponseEntity.ok(productRestMapper.toProductInShopDto(productInShopTo));
  }

  @Override
  public ResponseEntity<ShopDto> partialUpdateShop(Integer shopId, UpdateShopDto updateShopDto) {
    UpdateShopTo updateShopTo = shopRestMapper.toUpdateShopTo(updateShopDto);

    ShopTo shopTo = shopService.partialUpdateShop(shopId, updateShopTo);

    return ResponseEntity.ok(shopRestMapper.toShopDto(shopTo));
  }


  @Override
  public ResponseEntity<Void> deleteShop(Integer shopId) {
    shopService.deleteShop(shopId);

    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteProductFromShop(Integer productId, Integer shopId) {
    shopService.deleteProductFromShop(shopId, productId);

    return ResponseEntity.ok().build();
  }
}

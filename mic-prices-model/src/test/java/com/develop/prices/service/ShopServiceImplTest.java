package com.develop.prices.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.entity.ShopModel;
import com.develop.prices.exception.ConflictException;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductInShopModelMapper;
import com.develop.prices.mapper.ShopModelMapper;
import com.develop.prices.repository.ProductInShopRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.repository.ShopLocationRepository;
import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {

  @Mock private ProductInShopRepository productInShopRepository;

  @Mock private ShopLocationRepository shopLocationRepository;

  @Mock private ProductRepository productRepository;

  private ShopService shopService;

  @BeforeEach
  void setUp() {
    shopService =
        new ShopServiceImpl(
            productInShopRepository,
            shopLocationRepository,
            Mappers.getMapper(ShopModelMapper.class),
            Mappers.getMapper(ProductInShopModelMapper.class),
            productRepository);
  }

  @Test
  void testFindAllShopWithFiltersNoFilters() {
    Pageable pageable = PageRequest.of(0, 10);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(1);
    shop1.setCountry("España");
    shop1.setCity("Madrid");
    shop1.setAddress("Calle Gran Vía 1");

    ShopModel shop2 = new ShopModel();
    shop2.setShopId(2);
    shop2.setCountry("España");
    shop2.setCity("Barcelona");
    shop2.setAddress("Passeig de Gràcia 1");

    List<ShopModel> shopList = Arrays.asList(shop1, shop2);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, shopList.size());

    ShopTo shopTo1 = new ShopTo(1, "España", "Madrid", "Calle Gran Vía 1");
    ShopTo shopTo2 = new ShopTo(2, "España", "Barcelona", "Passeig de Gràcia 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result = shopService.findAllShopWithFilters(null, null, null, pageable);

    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    assertEquals(2, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertEquals(1, result.getContent().get(0).getShopId());
    assertEquals("España", result.getContent().get(0).getCountry());
    assertEquals("Madrid", result.getContent().get(0).getCity());
    assertEquals("Calle Gran Vía 1", result.getContent().get(0).getAddress());
  }

  @Test
  void testFindAllShopWithFiltersCountry() {
    String countryFilter = "España";
    Pageable pageable = PageRequest.of(0, 10);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(1);
    shop1.setCountry("España");
    shop1.setCity("Madrid");
    shop1.setAddress("Calle Gran Vía 1");

    List<ShopModel> shopList = List.of(shop1);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, shopList.size());

    ShopTo shopTo1 = new ShopTo(1, "España", "Madrid", "Calle Gran Vía 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result =
        shopService.findAllShopWithFilters(countryFilter, null, null, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertEquals("España", result.getContent().get(0).getCountry());

    verify(shopLocationRepository).findAll(any(Specification.class), eq(pageable));
  }

  @Test
  void testFindAllShopWithFiltersCity() {
    String cityFilter = "Barcelona";
    Pageable pageable = PageRequest.of(0, 10);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(2);
    shop1.setCountry("España");
    shop1.setCity("Barcelona");
    shop1.setAddress("Passeig de Gràcia 1");

    List<ShopModel> shopList = List.of(shop1);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, shopList.size());

    ShopTo shopTo1 = new ShopTo(2, "España", "Barcelona", "Passeig de Gràcia 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result =
        shopService.findAllShopWithFilters(null, cityFilter, null, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertEquals("Barcelona", result.getContent().get(0).getCity());
  }

  @Test
  void testFindAllShopWithFiltersAddress() {
    String addressFilter = "Gran Vía";
    Pageable pageable = PageRequest.of(0, 10);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(1);
    shop1.setCountry("España");
    shop1.setCity("Madrid");
    shop1.setAddress("Calle Gran Vía 1");

    List<ShopModel> shopList = List.of(shop1);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, shopList.size());

    ShopTo shopTo1 = new ShopTo(1, "España", "Madrid", "Calle Gran Vía 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result =
        shopService.findAllShopWithFilters(null, null, addressFilter, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.getContent().get(0).getAddress().contains(addressFilter));
  }

  @Test
  void testFindAllShopWithFiltersMultipleFilters() {
    String countryFilter = "España";
    String cityFilter = "Madrid";
    String addressFilter = "Gran Vía";
    Pageable pageable = PageRequest.of(0, 10);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(1);
    shop1.setCountry("España");
    shop1.setCity("Madrid");
    shop1.setAddress("Calle Gran Vía 1");

    List<ShopModel> shopList = List.of(shop1);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, shopList.size());

    ShopTo shopTo1 = new ShopTo(1, "España", "Madrid", "Calle Gran Vía 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result =
        shopService.findAllShopWithFilters(countryFilter, cityFilter, addressFilter, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertEquals("España", result.getContent().get(0).getCountry());
    assertEquals("Madrid", result.getContent().get(0).getCity());
    assertTrue(result.getContent().get(0).getAddress().contains(addressFilter));
  }

  @Test
  void testFindAllShopWithFiltersPagination() {
    Pageable pageable = PageRequest.of(1, 1);

    ShopModel shop1 = new ShopModel();
    shop1.setShopId(1);
    shop1.setCountry("España");
    shop1.setCity("Madrid");
    shop1.setAddress("Calle Gran Vía 1");

    ShopModel shop2 = new ShopModel();
    shop2.setShopId(2);
    shop2.setCountry("España");
    shop2.setCity("Barcelona");
    shop2.setAddress("Passeig de Gràcia 1");

    List<ShopModel> shopList = List.of(shop2);
    Page<ShopModel> shopPage = new PageImpl<>(shopList, pageable, 2);

    ShopTo shopTo2 = new ShopTo(2, "España", "Barcelona", "Passeig de Gràcia 1");

    when(shopLocationRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(shopPage);

    PageResponseTo<ShopTo> result = shopService.findAllShopWithFilters(null, null, null, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(2, result.getTotalElements());
    assertEquals(2, result.getTotalPages());
    assertEquals(2, result.getContent().get(0).getShopId());
  }

  @Test
  void testFindShopById() {
    Integer shopId = 1;
    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);

    when(shopLocationRepository.findById((shopId))).thenReturn(Optional.of(shopModel));

    ShopTo result = shopService.findShopById(shopId);

    assertEquals(shopId, result.getShopId());
  }

  @Test
  void testSaveShop() {
    ShopAddTo shopAddTo = new ShopAddTo();
    shopAddTo.setAddress("Calle 123");
    shopAddTo.setCity("Guatepeor");
    shopAddTo.setCountry("Guatemala");

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(1);
    shopModel.setAddress(shopAddTo.getAddress());
    shopModel.setCity(shopAddTo.getCity());
    shopModel.setCountry(shopAddTo.getCountry());

    when(shopLocationRepository.save(any(ShopModel.class))).thenReturn(shopModel);

    ShopTo result = shopService.saveShop(shopAddTo);

    assertEquals("Calle 123", result.getAddress());
    assertEquals("Guatepeor", result.getCity());
    assertEquals("Guatemala", result.getCountry());
  }

  @Test
  void testAddProductToShop() {
    Integer shopId = 1;
    Integer productId = 1;
    BigDecimal price = new BigDecimal("10.00");

    AddProductShopTo addProductShopTo = new AddProductShopTo();
    addProductShopTo.setPrice(price);

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);
    shopModel.setAddress("Calle 123");
    shopModel.setCity("Guatepeor");
    shopModel.setCountry("Guatemala");

    ProductModel productModel = new ProductModel();
    productModel.setProductId(productId);
    productModel.setName("Producto 1");

    ProductInShopModel productInShopModel = new ProductInShopModel();
    productInShopModel.setProduct(productModel);
    productInShopModel.setShop(shopModel);
    productInShopModel.setPrice(addProductShopTo.getPrice());

    when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));
    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.of(shopModel));
    when(productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId))
        .thenReturn(Optional.empty());
    when(productInShopRepository.save(any(ProductInShopModel.class)))
        .thenReturn(productInShopModel);

    ProductInShopTo result = shopService.addProductToShop(shopId, productId, addProductShopTo);

    assertEquals(productId, result.getProductId());
    assertEquals(shopId, result.getShopId());
    assertEquals(price, result.getPrice());
  }

  @Test
  void testAddProductToShopNotFound() {
    Integer shopId = 99;
    Integer productId = 99;
    BigDecimal price = new BigDecimal("10.00");

    AddProductShopTo addProductShopTo = new AddProductShopTo();
    addProductShopTo.setPrice(price);

    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.empty());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          shopService.addProductToShop(shopId, productId, addProductShopTo);
        });
  }

  @Test
  void testAddProductToShopProductConflict() {
    Integer productId = 1;
    Integer shopId = 1;
    BigDecimal price = new BigDecimal("10.00");

    AddProductShopTo addProductShopTo = new AddProductShopTo();
    addProductShopTo.setPrice(price);

    ProductModel productModel = new ProductModel();
    productModel.setProductId(productId);

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);

    when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));
    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.of(shopModel));
    when(productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId))
        .thenThrow(new ConflictException());

    assertThrows(
        ConflictException.class,
        () -> {
          shopService.addProductToShop(shopId, productId, addProductShopTo);
        });
  }

  @Test
  void testUpdateShop() {
    Integer shopId = 1;

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);
    shopModel.setAddress("Calle 123");
    shopModel.setCity("Guatepeor");
    shopModel.setCountry("Guatemala");

    ShopPutTo shopPutTo = new ShopPutTo();
    shopPutTo.setAddress("La casita de brais");
    shopPutTo.setCity("Coruña");
    shopPutTo.setCountry("Es complicado");

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.of(shopModel));
    when(shopLocationRepository.save(any(ShopModel.class))).thenReturn(shopModel);

    ShopTo result = shopService.updateShop(shopId, shopPutTo);

    assertNotNull(result);
    assertEquals(shopId, result.getShopId());
    assertEquals("La casita de brais", result.getAddress());
    assertEquals("Coruña", result.getCity());
    assertEquals("Es complicado", result.getCountry());
  }

  @Test
  void testUpdateShopNotFound() {
    Integer shopId = 99;

    ShopPutTo shopPutTo = new ShopPutTo();
    shopPutTo.setAddress("La casita de brais");
    shopPutTo.setCity("Coruña");
    shopPutTo.setCountry("Es complicado");

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.empty());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          shopService.updateShop(shopId, shopPutTo);
        });
  }

  @Test
  void testPartialUpdateShop() {
    Integer shopId = 1;

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);
    shopModel.setAddress("Calle 123");
    shopModel.setCity("Guatepeor");
    shopModel.setCountry("Guatemala");

    UpdateShopTo updateShopTo = new UpdateShopTo();
    updateShopTo.setAddress("La casita de brais");
    updateShopTo.setCity("Coruña");
    updateShopTo.setCountry("Es complicado");

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.of(shopModel));

    ShopTo result = shopService.partialUpdateShop(shopId, updateShopTo);

    assertNotNull(result);
    assertEquals(shopId, result.getShopId());
    assertEquals("La casita de brais", result.getAddress());
    assertEquals("Coruña", result.getCity());
    assertEquals("Es complicado", result.getCountry());
  }

  @Test
  void testPartialUpdateShopNotFound() {
    Integer shopId = 99;

    UpdateShopTo updateShopTo = new UpdateShopTo();
    updateShopTo.setAddress("La casita de brais");

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.empty());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          shopService.partialUpdateShop(shopId, updateShopTo);
        });
  }

  @Test
  void testUpdateProductPriceInShop() {
    Integer shopId = 1;
    Integer productId = 1;
    Integer productInShopId = 1;
    BigDecimal price = new BigDecimal("10.00");

    ProductModel productModel = new ProductModel();
    productModel.setProductId(productId);

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);

    ProductInShopModel productInShopModel = new ProductInShopModel();
    productInShopModel.setProductInShopId(productInShopId);
    productInShopModel.setProduct(productModel);
    productInShopModel.setShop(shopModel);
    productInShopModel.setPrice(new BigDecimal("20.00"));

    ProductInShopPatchTo productInShopPatchTo = new ProductInShopPatchTo();
    productInShopPatchTo.setPrice(price);

    when(productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId))
        .thenReturn(Optional.of(productInShopModel));
    when(productInShopRepository.save(any(ProductInShopModel.class)))
        .thenReturn(productInShopModel);

    ProductInShopTo result =
        shopService.updateProductPriceInShop(shopId, productId, productInShopPatchTo);

    assertNotNull(result);
    assertEquals(productInShopId, result.getProductInShopId());
    assertEquals(productId, result.getProductId());
    assertEquals(shopId, result.getShopId());
    assertEquals(price, result.getPrice());
  }

  @Test
  void testDeleteShop() {
    Integer shopId = 1;

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.of(shopModel));

    shopService.deleteShop(shopId);
  }

  @Test
  void testDeleteShopNotFound() {
    Integer shopId = 99;

    when(shopLocationRepository.findById(shopId)).thenReturn(Optional.empty());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          shopService.deleteShop(shopId);
        });
  }

  @Test
  void testDeleteProductFromShop() {
    Integer shopId = 1;
    Integer productId = 1;
    Integer productInShopId = 1;

    ProductModel productModel = new ProductModel();
    productModel.setProductId(productId);

    ShopModel shopModel = new ShopModel();
    shopModel.setShopId(shopId);

    ProductInShopModel productInShopModel = new ProductInShopModel();
    productInShopModel.setProductInShopId(productInShopId);
    productInShopModel.setProduct(productModel);
    productInShopModel.setShop(shopModel);
    productInShopModel.setPrice(new BigDecimal("20.00"));

    when(productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId))
        .thenReturn(Optional.of(productInShopModel));

    shopService.deleteProductFromShop(shopId, productId);
  }

  @Test
  void testDeleteProductFromShopNotFound() {
    Integer shopId = 99;
    Integer productId = 99;

    when(productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId))
        .thenReturn(Optional.empty());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          shopService.deleteProductFromShop(shopId, productId);
        });
  }
}

package com.develop.prices.rest;

import com.develop.prices.controller.ProductsApi;
import com.develop.prices.dto.PageResponseDtoProductWithShopsDto;
import com.develop.prices.dto.ProductDto;
import com.develop.prices.dto.ProductNameDto;
import com.develop.prices.dto.ProductWithShopsDto;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.service.ProductService;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
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
public class ProductController implements ProductsApi {
  private final ProductService productService;

  private final ProductRestMapper productRestMapper;

  @Autowired
  public ProductController(ProductService productService, ProductRestMapper productRestMapper) {
    this.productService = productService;
    this.productRestMapper = productRestMapper;
  }

  @Override
  public ResponseEntity<PageResponseDtoProductWithShopsDto> getProductsWithFilters(
      String name, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable) {
    PageResponseTo<ProductWithShopsTo> productTos =
        productService.findAllProductsWithFilters(name, priceMin, priceMax, pageable);

    pageable =
        PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "productId"));

    List<ProductWithShopsDto> productDtos =
        productTos.getContent().stream().map(productRestMapper::toProductWithShopsDto).toList();

    PageResponseDtoProductWithShopsDto response = new PageResponseDtoProductWithShopsDto();
    response.setContent(productDtos);
    response.setTotalElements(productTos.getTotalElements());
    response.setTotalPages(productTos.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ProductWithShopsDto> getProductById(Integer productId) {
    ProductWithShopsTo productWithShopsTo = productService.findByProductById(productId);

    return ResponseEntity.ok(productRestMapper.toProductWithShopsDto(productWithShopsTo));
  }


  @Override
  public ResponseEntity<ProductDto> addProduct(ProductNameDto productNameDto) {
    ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDto);

    ProductTo savedProduct = productService.saveProduct(productNameTo);

    ProductDto productDto = productRestMapper.toProductDto(savedProduct);

    return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
  }

  @Override
  public ResponseEntity<ProductDto> updateProduct(
      Integer productId, ProductNameDto productNameDto) {
    ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDto);

    ProductTo updateProductTo = productService.updateProduct(productId, productNameTo);

    ProductDto updateProductDto = productRestMapper.toProductDto(updateProductTo);

    return ResponseEntity.ok(updateProductDto);
  }

  @Override
  public ResponseEntity<Void> deleteProduct(Integer productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok().build();
  }
}

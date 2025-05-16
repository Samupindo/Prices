package com.develop.prices.rest;

import com.develop.prices.dto.PageResponseDto;
import com.develop.prices.dto.ProductDto;
import com.develop.prices.dto.ProductNameDto;
import com.develop.prices.dto.ProductWithShopsDto;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.service.ProductService;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  private final ProductRestMapper productRestMapper;

  @Autowired
  public ProductController(ProductService productService, ProductRestMapper productRestMapper) {
    this.productService = productService;
    this.productRestMapper = productRestMapper;
  }

  @GetMapping("")
  public ResponseEntity<PageResponseDto<ProductWithShopsDto>> getProductsWithFilters(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) BigDecimal priceMin,
      @RequestParam(required = false) BigDecimal priceMax,
      @PageableDefault(sort = "productId", direction = Sort.Direction.ASC) Pageable pageable) {

    PageResponseTo<ProductWithShopsTo> productTos =
        productService.findAllProductsWithFilters(name, priceMin, priceMax, pageable);

    List<ProductWithShopsDto> productDtos =
        productTos.getContent().stream().map(productRestMapper::toProductWithShopsDto).toList();

    PageResponseDto<ProductWithShopsDto> response =
        new PageResponseDto<>(productDtos, productDtos.size(), 1);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductWithShopsDto> getProductById(@PathVariable Integer productId) {
    ProductWithShopsTo productWithShopsTo = productService.findByProductById(productId);

    return ResponseEntity.ok(productRestMapper.toProductWithShopsDto(productWithShopsTo));
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
  public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductNameDto productNameDto) {
    ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDto);

    ProductTo savedProduct = productService.saveProduct(productNameTo);

    ProductDto productDto = productRestMapper.toProductDto(savedProduct);

    return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable Integer productId, @Valid @RequestBody ProductNameDto productNameDto) {
    ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDto);

    ProductTo updateProductTo = productService.updateProduct(productId, productNameTo);

    ProductDto updateProductDto = productRestMapper.toProductDto(updateProductTo);

    return ResponseEntity.ok(updateProductDto);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok().build();
  }
}

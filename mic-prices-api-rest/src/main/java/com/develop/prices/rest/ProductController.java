package com.develop.prices.rest;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.service.ProductService;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


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
    public ResponseEntity<PageResponse<ProductWithShopsDTO>> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @PageableDefault(sort = "productId", direction = Sort.Direction.ASC) Pageable pageable) {

        PageResponse<ProductWithShopsTo> productTos = productService.findAllProductsWithFilters(name, priceMin, priceMax, pageable);

        List<ProductWithShopsDTO> productDTOS = productTos.getContent().stream()
                .map(productRestMapper::toProductWithShopsDTO)
                .toList();

        PageResponse<ProductWithShopsDTO> response = new PageResponse<>(
                productDTOS,
                productDTOS.size(),
                1
        );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductWithShopsDTO> getProductById(@PathVariable Integer productId) {
        ProductWithShopsTo productWithShopsTo = productService.findByProductById(productId);

        if (productWithShopsTo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(productRestMapper.toProductWithShopsDTO(productWithShopsTo));
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Missing required field: name\" }"
                            )
                    )
            )
    })
    @PostMapping("")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductNameDTO productNameDTO) {
        ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDTO);

        ProductTo savedProduct = productService.saveProduct(productNameTo);

        ProductDTO productDTO = productRestMapper.toProductDTO(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductNameDTO productNameDTO) {
        ProductNameTo productNameTo = productRestMapper.toProductNameTo(productNameDTO);

        ProductTo updateProductTo = productService.updateProduct(productId, productNameTo);

        ProductDTO updateProductDTO = productRestMapper.toProductDTO(updateProductTo);

        return ResponseEntity.ok(updateProductDTO);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }


}

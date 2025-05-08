package com.develop.prices.rest;

import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.service.ProductService;
import com.develop.prices.to.PageResponse;
import com.develop.prices.to.ProductWithShopsTo;
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
//
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "Created",
//                    content = @Content(mediaType = "application/json")
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid input",
//                    content = @Content(mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Missing required field: name\" }"
//                            )
//                    )
//            )
//    })
//    @PostMapping("")
//    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductNameDTO productNameDTO) {
//        // Crear nuevo producto
//        ProductModel productModel = new ProductModel();
//        productModel.setName(productNameDTO.getName());
//
//        // Guardar en base de datos
//        ProductModel savedProduct = productRepository.save(productModel);
//
//        // Devolver DTO como respuesta
//        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toProductDTO(savedProduct));
//    }
//
//    @PutMapping("/{productId}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductNameDTO productNameDTO) {
//        ProductModel productModel = productRepository.findById(productId).orElse(null);
//        if (productModel == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        productModel.setName(productNameDTO.getName());
//        return ResponseEntity.ok(productMapper.toProductDTO(productModel));
//    }
//
//
//    @DeleteMapping("/{productId}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
//        if (productRepository.existsById(productId)) {
//            productRepository.deleteById(productId);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


}

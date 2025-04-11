package com.develop.prices.controller;

import com.develop.prices.modelo.dto.ProductDTO;
import com.develop.prices.modelo.dto.ProductNameDTO;
import com.develop.prices.modelo.dto.ProductWithShopsDTO;
import com.develop.prices.modelo.dto.ShopInfoDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("")

public class ProductController {

    private List<ProductDTO> products = new ArrayList<>();
    private List<ShopInfoDTO> shopInfoDTOS = new ArrayList<>();


    public ProductController() {
        products.add(new ProductDTO("Pizza con piña"));
        products.add(new ProductDTO("Agua"));
        products.add(new ProductDTO("Agua con gas"));
        products.add(new ProductDTO("Naranjas"));

        shopInfoDTOS.add(new ShopInfoDTO(1, new BigDecimal("10.5")));
        shopInfoDTOS.add(new ShopInfoDTO(2, new BigDecimal("9.5")));
        shopInfoDTOS.add(new ShopInfoDTO(3, new BigDecimal("11")));
        shopInfoDTOS.add(new ShopInfoDTO(4, new BigDecimal("10")));
    }

    @GetMapping("/products")
    public List<ProductWithShopsDTO> getProductsWithShops() {
        List<ProductWithShopsDTO> producto = new ArrayList<>();

        for (ProductDTO productModel : products) {
            producto.add(new ProductWithShopsDTO(productModel.getProductId(), productModel.getName(), shopInfoDTOS));
        }

        return producto;
    }

    @GetMapping("/product/{productId}")
    public List<ProductWithShopsDTO> getProductsWithId(@PathVariable Integer productId) {
        for (ProductDTO productModel : products) {
            if (productModel.getProductId().equals(productId)) {
                return List.of(new ProductWithShopsDTO(
                        productModel.getProductId(),
                        productModel.getName(),
                        shopInfoDTOS
                ));
            }
        }
        return Collections.emptyList(); //debería devolver 404
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
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductNameDTO productNameDTO) {
        if (productNameDTO.getName() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That field does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    //Arreglar nextId
        ProductDTO newProduct = new ProductDTO();
        newProduct.setName(productNameDTO.getName()); //hay que poner límite al name
        products.add(newProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Integer productId) {
        boolean removed = products.removeIf(product -> product.getProductId().equals(productId));

        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> updateProducts(@Validated @PathVariable Integer productId, @RequestBody ProductNameDTO productNameDTO) {
        for (int i = 0; i < products.size(); i++) {
            ProductDTO currentProduct = products.get(i);
            if (currentProduct.getProductId().equals(productId)) {
                if (productNameDTO.getName() == null) {
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body("That field does not exist");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                } else if (productNameDTO.getName().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    //
                }
                currentProduct.setName(productNameDTO.getName());
                return ResponseEntity.ok(currentProduct);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products/filter")
    public List<ProductWithShopsDTO> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {

        List<ProductWithShopsDTO> filteredProducts = new ArrayList<>();


        for (ProductDTO product : products) {
            List<ShopInfoDTO> filteredShops = new ArrayList<>(shopInfoDTOS);

            if (priceMin != null) {
                filteredShops.removeIf(shop -> shop.getPrice().compareTo(priceMin) < 0);
            }

            if (priceMax != null) {
                filteredShops.removeIf(shop -> shop.getPrice().compareTo(priceMax) > 0);
            }

            if (!filteredShops.isEmpty()) {
                filteredProducts.add(new ProductWithShopsDTO(
                        product.getProductId(),
                        product.getName(),
                        filteredShops
                ));
            }
        }
        if (name != null) {
            filteredProducts.removeIf(p -> !p.getName().toLowerCase().contains(name.toLowerCase()));
        }

        if (filteredProducts.isEmpty()){
            return Collections.emptyList();

        }else{
            return filteredProducts;
        }

    }



}

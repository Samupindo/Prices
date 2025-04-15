package com.develop.prices.controller;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.dto.ProductDTO;
import com.develop.prices.model.dto.ProductNameDTO;
import com.develop.prices.model.dto.ProductWithShopsDTO;
import com.develop.prices.model.dto.ShopInfoDTO;
import com.develop.prices.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private List<ProductDTO> products = new ArrayList<>();
    private List<ShopInfoDTO> shopInfoDTOS = new ArrayList<>();
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository; // Asignar el repositorio
    }

    @GetMapping("/all")
    public List<ProductModel> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("")
    public List<ProductWithShopsDTO> getProductsWithShops() {
        List<ProductModel> productModels = productRepository.findAll();  // Obtener productos de la base de datos
        List<ProductWithShopsDTO> productWithShops = new ArrayList<>();

        for (ProductModel productModel : productModels) {
            // Asociar tiendas a cada producto (esto lo haces con shopInfoDTOS)
            productWithShops.add(new ProductWithShopsDTO(
                    productModel.getProductId(),
                    productModel.getName(),
                    shopInfoDTOS
            ));
        }
        return productWithShops;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductWithShopsDTO> getProductById(@PathVariable Integer productId) {
        ProductModel productModel = productRepository.findById(productId).orElse(null);
        if (productModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new ProductWithShopsDTO(
                productModel.getProductId(),
                productModel.getName(),
                shopInfoDTOS
        ));
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
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductNameDTO productNameDTO) {
        if (productNameDTO.getName() == null || productNameDTO.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (productNameDTO.getName().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ProductDTO(null, "Name is too long"));
        }

        // Crear nuevo producto
        ProductModel newProduct = new ProductModel();
        newProduct.setName(productNameDTO.getName());

        // Guardar en base de datos
        ProductModel savedProduct = productRepository.save(newProduct);

        // Devolver DTO como respuesta
        ProductDTO productDTO = new ProductDTO(savedProduct.getProductId(), savedProduct.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer productId, @RequestBody ProductNameDTO productNameDTO) {
        ProductModel existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (productNameDTO.getName() == null || productNameDTO.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        existingProduct.setName(productNameDTO.getName());
        productRepository.save(existingProduct);

        return ResponseEntity.ok(new ProductDTO(existingProduct.getProductId(), existingProduct.getName()));
    }


    @GetMapping("/filter")
    public List<ProductWithShopsDTO> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {

        // Obtener todos los productos desde la base de datos
        List<ProductModel> productModels = productRepository.findAll();
        List<ProductWithShopsDTO> filteredProducts = new ArrayList<>();

        for (ProductModel product : productModels) {
            List<ShopInfoDTO> filteredShops = new ArrayList<>(shopInfoDTOS);

            // Filtrar por el precio mínimo
            if (priceMin != null) {
                filteredShops.removeIf(shop -> shop.getPrice().compareTo(priceMin) < 0);
            }

            // Filtrar por el precio máximo
            if (priceMax != null) {
                filteredShops.removeIf(shop -> shop.getPrice().compareTo(priceMax) > 0);
            }

            // Si hay tiendas filtradas y el producto tiene tiendas asociadas
            if (!filteredShops.isEmpty()) {
                // Filtrar por nombre, si se proporciona
                if (name != null && !product.getName().toLowerCase().contains(name.toLowerCase())) {
                    continue; // Si no contiene el nombre, saltar este producto
                }

                // Crear el DTO para el producto con las tiendas filtradas
                filteredProducts.add(new ProductWithShopsDTO(
                        product.getProductId(),
                        product.getName(),
                        filteredShops
                ));
            }
        }

        // Si no se encuentra ningún producto filtrado, devolver una lista vacía
        return filteredProducts;
    }

}

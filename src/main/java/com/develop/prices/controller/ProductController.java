package com.develop.prices.controller;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.model.dto.*;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.repository.ShopLocationRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/products")
@Transactional
public class ProductController {
    private List<ProductDTO> products = new ArrayList<>();
    private List<ShopInfoDTO> shopInfoDTOS = new ArrayList<>();
    private ProductRepository productRepository;
    private ShopLocationRepository shopRepository;
    private ProductPriceRepository productPriceRepository;


    public ProductController(ProductRepository productRepository, ShopLocationRepository shopRepository, ProductPriceRepository productPriceRepository) {
        this.productRepository = productRepository; // Asignar el repositorio
        this.shopRepository = shopRepository;
        this.productPriceRepository = productPriceRepository;
    }

//    @GetMapping("")
//    public List<ProductModel> getProducts() {
//        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productId")); // Ordenar por ID ascendente
//    }

    @GetMapping("")
    public List<ProductModel> getProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productId")); // Ordenar por ID ascendente
    }

    @GetMapping("/{product_id}")
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


    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer productId, @RequestBody ProductNameDTO productNameDTO) {
        ProductModel existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (productNameDTO.getName() == null || productNameDTO.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        existingProduct.setName(productNameDTO.getName());


        return ResponseEntity.ok(new ProductDTO(existingProduct.getProductId(), existingProduct.getName()));
    }


    @GetMapping("/filter")
    public List<ProductWithShopsDTO> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {

        // Obtener todos los productos desde la base de datos
        List<ProductModel> productModels = productRepository.findAll();
        List<ProductWithShopsDTO> productWithShopsDTOS;

        List<ShopModel> filteredShops = shopRepository.findAll();
        List<ProductPriceModel> productPriceModel = productPriceRepository.findAll();

        List<BigDecimal> prices = productPriceModel.stream()
                .map(ProductPriceModel::getPrice)
                .toList();

        List<Integer> shopIds = filteredShops.stream()
                .map(ShopModel::getShopId)
                .toList();


        List<ShopInfoDTO> shopsInfo = IntStream.range(0,shopIds.size())   //genera indices (del tamaño de la lista que se le pase) para sincronizar los elementos por posicion
                .mapToObj(i->new ShopInfoDTO(shopIds.get(i), prices.get(i)))
                .toList();


        List<ShopInfoDTO> shopsInfoFiltered = shopsInfo.stream()
                .filter(p -> p.getPrice().compareTo(priceMin) > 0 && p.getPrice().compareTo(priceMax) < 0)
                .toList();



        productWithShopsDTOS = IntStream.range(0,productModels.size())
                .mapToObj(i-> new ProductWithShopsDTO(productModels.get(i).getProductId(),productModels.get(i).getName(),shopsInfoFiltered))
                .toList();


        productWithShopsDTOS = productWithShopsDTOS.stream()
                .filter(n -> n.getName().contains(name))
                .toList();

        // Si no se encuentra ningún producto filtrado, devolver una lista vacía
        return productWithShopsDTOS;
    }



}

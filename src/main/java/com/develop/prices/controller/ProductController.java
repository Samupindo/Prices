package com.develop.prices.controller;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.model.dto.ProductDTO;
import com.develop.prices.model.dto.ProductNameDTO;
import com.develop.prices.model.dto.ProductWithShopsDTO;
import com.develop.prices.model.dto.ShopInfoDTO;
import com.develop.prices.repository.ProductRepository;
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

@Transactional
@RestController
@RequestMapping("/products")
public class ProductController {
    private List<ProductDTO> products = new ArrayList<>();
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository; // Asignar el repositorio
    }

    @GetMapping("")
    public List<ProductWithShopsDTO> getProducts() {
        List<ProductModel> productModels = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productId"));
        List<ProductWithShopsDTO> productWithShopsList = new ArrayList<>();

        //Recorremos el producto y  creamos una lista vacia donde se guardaran los precios
        for (ProductModel product : productModels) {
            List<ShopInfoDTO> shopInfoList = new ArrayList<>();

            if (product.getPrices() != null) {
                //Recorremos los productos con el precio y tienda asociado, lo guardamos en la lista mediante ShopInfoDTO
                for (ProductPriceModel priceModel : product.getPrices()) {
                    ShopInfoDTO shopInfo = new ShopInfoDTO(
                            priceModel.getShop().getShopId(),
                            priceModel.getPrice()
                    );
                    shopInfoList.add(shopInfo);
                }
            }
            // DTO con la info de la tienda completa
            ProductWithShopsDTO productWithShopsDTO = new ProductWithShopsDTO(
                    product.getProductId(),
                    product.getName(),
                    shopInfoList
            );
            productWithShopsList.add(productWithShopsDTO);
        }
        return productWithShopsList;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductWithShopsDTO> getProductById(@PathVariable Integer productId) {
        ProductModel productModel = productRepository.findById(productId).orElse(null);
        if (productModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Preparamos lista vacía para guardar los precios
        List<ShopInfoDTO> shopInfoDTOS = new ArrayList<>();

        if (productModel.getPrices() != null) {
            //Recorremos el producto obtenido,
            // productModel.getPrices() indica en que tiendas se vende el producto
            for (ProductPriceModel priceModel : productModel.getPrices()) {
                ShopInfoDTO shopInfo = new ShopInfoDTO(
                        priceModel.getShop().getShopId(),
                        priceModel.getPrice()
                );
                shopInfoDTOS.add(shopInfo);
            }
        }
        //Creamos el objeto que se mostrara
        ProductWithShopsDTO productWithShop = new ProductWithShopsDTO(
                productModel.getProductId(),
                productModel.getName(),
                shopInfoDTOS
        );
        return ResponseEntity.ok(productWithShop);
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
                    .build();
        }
        if (!productNameDTO.getName().matches("[A-Za-z\\s]+")) {
            return ResponseEntity.badRequest().build();
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


        return ResponseEntity.ok(new ProductDTO(existingProduct.getProductId(), existingProduct.getName()));
    }


    @GetMapping("/filter")
    public List<ProductWithShopsDTO> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {


        List<ProductModel> productModels = productRepository.findAll();
        List<ProductWithShopsDTO> filteredProducts = new ArrayList<>();

        for (ProductModel product : productModels) {
            if (name != null && !product.getName().toLowerCase().contains(name.toLowerCase())) {
                continue;
            }

            List<ShopInfoDTO> filteredShops = new ArrayList<>();

            if (product.getPrices() != null) {
                    //Recorremos los precios para este producto
                for (ProductPriceModel priceModel : product.getPrices()) {
                    BigDecimal price = priceModel.getPrice();

                    boolean isWithinMin = (priceMin == null || price.compareTo(priceMin) >= 0);
                    boolean isWithinMax = (priceMax == null || price.compareTo(priceMax) <= 0);

                    if (isWithinMin && isWithinMax) {
                        //Se comprueba que la tienda no es null
                        ShopModel shop = priceModel.getShop();
                        if (shop != null) {
                            filteredShops.add(new ShopInfoDTO(shop.getShopId(), price));
                        }
                    }
                }
            }

            //Comprobar si hay tiendas con esos filtros
            if (!filteredShops.isEmpty()) {
                //Filtrar por nombre, si no coindice salta al final
                // Agregar el producto con sus tiendas filtradas
                filteredProducts.add(new ProductWithShopsDTO(
                        product.getProductId(),
                        product.getName(),
                        filteredShops
                ));
            }
        }

        return filteredProducts;
    }

}

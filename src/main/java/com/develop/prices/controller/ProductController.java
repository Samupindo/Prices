package com.develop.prices.controller;

import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.dto.*;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.ProductPriceSpecification;
import com.develop.prices.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
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
    private final ProductPriceRepository productPriceRepository;


    public ProductController(ProductRepository productRepository, ProductPriceRepository productPriceRepository) {
        this.productRepository = productRepository; // Asignar el repositorio
        this.productPriceRepository = productPriceRepository;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse> getProducts(@PageableDefault(sort = "productId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductModel> productPage = productRepository.findAll(pageable);
        List<ProductWithShopsDTO> productWithShopsDTOList = new ArrayList<>();

        for (ProductModel product : productPage.getContent()) {
            List<ShopInfoDTO> shopList = new ArrayList<>();
            if (product.getPrices() != null) {
                for (ProductPriceModel price : product.getPrices()) {
                    shopList.add(new ShopInfoDTO(price.getShop().getShopId(), price.getPrice()));
                }
            }

            productWithShopsDTOList.add(new ProductWithShopsDTO(
                    product.getProductId(),
                    product.getName(),
                    shopList
            ));
        }

        PageResponse PageResponse = new PageResponse(
                productWithShopsDTOList,
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );

        return ResponseEntity.ok(PageResponse);
    }
     //Spring por defecto pagina cada 20 elementos, para cambiarlo añadir en size y cantidad en el @PageableDefault


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
    public ResponseEntity<List<ProductWithShopsDTO>> getProductsWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {

        Specification<ProductModel> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(ProductPriceSpecification.hasName(name));
        }

        if (priceMin != null) {
            spec = spec.and(ProductPriceSpecification.hasPriceMin(priceMin));
        }

        if (priceMax != null) {
            spec = spec.and(ProductPriceSpecification.hasPriceMax(priceMax));
        }

        List<ProductModel> products = productRepository.findAll(spec);
        List<ProductWithShopsDTO> productWithShopsDTOList = new ArrayList<>();

        for (ProductModel product : products) {
            List<ShopInfoDTO> shops = new ArrayList<>();

            if (product.getPrices() != null) {
                for (ProductPriceModel price : product.getPrices()) {
                    // Volvemos a verificar rango de precio aquí si se desea precisión extra
                    if ((priceMin == null || price.getPrice().compareTo(priceMin) >= 0) &&
                            (priceMax == null || price.getPrice().compareTo(priceMax) <= 0)) {

                        shops.add(new ShopInfoDTO(price.getShop().getShopId(), price.getPrice()));
                    }
                }
            }

            // Se agregará el producto aunque no tenga tiendas
            productWithShopsDTOList.add(new ProductWithShopsDTO(product.getProductId(), product.getName(), shops));
        }

        return ResponseEntity.ok(productWithShopsDTOList);
    }



}

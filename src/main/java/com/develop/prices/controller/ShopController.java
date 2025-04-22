package com.develop.prices.controller;


import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.model.dto.*;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.repository.ShopLocationRepository;
import com.develop.prices.repository.ShopsSpecification;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/shops")
public class ShopController {

    private final ShopLocationRepository shopLocationRepository;
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    public ShopController(ShopLocationRepository shopLocationRepository, ProductRepository productRepository, ProductPriceRepository productPriceRepository) {
        this.shopLocationRepository = shopLocationRepository;
        this.productRepository = productRepository;
        this.productPriceRepository = productPriceRepository;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse> getAllShops(
            @PageableDefault(sort = "shopId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ShopModel> shopPage = shopLocationRepository.findAll(pageable);
        List<ShopDTO> shopDTOList = shopPage.getContent().stream().map(this::toShopDTO).toList();

        PageResponse pageResponse = new PageResponse(
                shopDTOList,
                shopPage.getTotalElements(),
                shopPage.getTotalPages()
        );
        return ResponseEntity.ok(pageResponse);

    }

    @GetMapping("/{shopId}")
    public ResponseEntity<List<ShopDTO>> getShopLocationDTO(@PathVariable Integer shopId) {

        ShopModel shopModel = (ShopModel) shopLocationRepository.findById(shopId).orElse(null);
        if (shopModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(List.of(toShopDTO(shopModel)));

    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"shopId\": 4, \"country\": \"España\", \"city\": \"Coruña\", \"address\": \"Os Mallos 10\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Missing or invalid fields",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Missing required field: city\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Shop already exists",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Shop already exists at this address in this city and country\" }"
                            )
                    )
            )
    })
    @PostMapping("")
    public ResponseEntity<ShopDTO> addShop(@Validated @RequestBody ShopAddDTO shopAddDTO) {

        ShopModel newShopModel = new ShopModel();

        newShopModel.setCountry(shopAddDTO.getCountry());
        newShopModel.setCity(shopAddDTO.getCity());
        newShopModel.setAddress(shopAddDTO.getAddress());

        ShopModel shopModel = shopLocationRepository.save(newShopModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(toShopDTO(shopModel));
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product added to shop",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"productId\": 1, \"shopId\": 2, \"price\": 15.99 }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid price provided",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Price must be greater than or equal to 0\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shop or product not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Product or shop not found\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product already exists in shop",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"This product is already registered in this shop\" }"
                            )
                    )
            )
    })
    @PostMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ProductPriceDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId, @Validated @RequestBody AddProductShopDTO addProductShopDTO) {

        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

        if (optionalProductModel.isEmpty() || optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        BigDecimal price = addProductShopDTO.getPrice();

        ProductPriceModel productPriceModel = buildProductPriceModel(optionalProductModel.get(), optionalShopModel.get(), price);


        ProductPriceModel productPriceModelDB = productPriceRepository.save(productPriceModel);


        return ResponseEntity.ok(toProductPriceDTO(productPriceModelDB));

    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "The shop has been deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shop not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Shop not found\" }"
                            )
                    )
            ),
    })

    @DeleteMapping("/{shopId}")
    public ResponseEntity<ShopDTO> deleteShop(@PathVariable Integer shopId) {
        if (shopLocationRepository.findById(shopId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        shopLocationRepository.deleteById(shopId);

        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "The shop has been deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shop not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Shop not found\" }"
                            )
                    )
            ),
    })

    @DeleteMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ProductPriceModel> deleteProductFromShop(@PathVariable Integer productId, @PathVariable Integer shopId) {

        ProductPriceModel productPriceModel = productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if (productPriceModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        shopLocationRepository.deleteById(shopId);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer shopId, @Valid @RequestBody UpdateShopDTO updateShopDTO) {

        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShopModel.get();
        shopModel.setCountry(updateShopDTO.getCountry());
        shopModel.setCity(updateShopDTO.getCity());
        shopModel.setAddress(updateShopDTO.getAddress());

        ShopModel saveShopModel = shopLocationRepository.save(shopModel);

        return ResponseEntity.ok(toShopDTO(saveShopModel));
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId, @Valid @RequestBody UpdateShopDTO updateShopDTO) {

        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShopModel.get();

        // Actualizar solo los campos no nulos
        if (updateShopDTO.getCountry() != null) {
            shopModel.setCountry(updateShopDTO.getCountry());
        }
        if (updateShopDTO.getCity() != null) {
            shopModel.setCity(updateShopDTO.getCity());
        }
        if (updateShopDTO.getAddress() != null) {
            shopModel.setAddress(updateShopDTO.getAddress());
        }
        ShopModel saveShopModel = shopLocationRepository.save(shopModel);

        return ResponseEntity.ok(toShopDTO(saveShopModel));

    }

    @PatchMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@PathVariable Integer shopId, @PathVariable Integer productId, @Validated @RequestBody ProductPricePatchDTO productPricePatchDTO) {

        ProductPriceModel productPriceModel = productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if(productPriceModel == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        BigDecimal price = productPricePatchDTO.getPrice();

        productPriceModel.setPrice(price);

        ProductPriceModel savePriceModel = productPriceRepository.save(productPriceModel);

        return ResponseEntity.ok(toProductPriceDTO(savePriceModel));
    }


    @GetMapping("/filter")
    public ResponseEntity<PageResponse<ShopDTO>> getShopLocationWithFilters(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @PageableDefault(sort = "shopId", direction = Sort.Direction.ASC) Pageable pageable) {

        Specification<ShopModel> spec = Specification.where(null);

        if (country != null) {
            spec = spec.and(ShopsSpecification.findByCountry(country));
        }

        if (city != null) {
            spec = spec.and(ShopsSpecification.findByCity(city));
        }

        if (address != null) {
            spec = spec.and(ShopsSpecification.findByAddress(address));
        }


        Page<ShopModel> shopModelPage = shopLocationRepository.findAll(spec, pageable);
        List<ShopDTO> shopDTOList = shopModelPage.getContent()
                .stream()
                //.map(s -> toShopDTO(s))
                .map(this::toShopDTO)
                .toList();

        PageResponse<ShopDTO> shopDTOPageResponse = new PageResponse<>(
                shopDTOList,
                shopModelPage.getTotalElements(),
                shopModelPage.getTotalPages()
        );

        return ResponseEntity.ok(shopDTOPageResponse);

    }

    private ProductPriceModel buildProductPriceModel(ProductModel product, ShopModel shop, BigDecimal price) {
        ProductPriceModel productPriceModel = new ProductPriceModel();
        productPriceModel.setProduct(product);
        productPriceModel.setShop(shop);
        productPriceModel.setPrice(price);

        return productPriceModel;
    }

    private ProductPriceDTO toProductPriceDTO(ProductPriceModel productPriceModel) {
        ProductPriceDTO productPriceDTO = new ProductPriceDTO();
        productPriceDTO.setShopId(productPriceModel.getShop().getShopId());
        productPriceDTO.setProductId(productPriceModel.getProduct().getProductId());
        productPriceDTO.setPrice(productPriceModel.getPrice());
        return productPriceDTO;
    }

    private ShopDTO toShopDTO(ShopModel shopModel) {
        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setShopId(shopModel.getShopId());
        shopDTO.setCountry(shopModel.getCountry());
        shopDTO.setCity(shopModel.getCity());
        shopDTO.setAddress(shopModel.getAddress());

        return shopDTO;
    }
    //orika, dozer, mapstruct
}


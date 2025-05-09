package com.develop.prices.rest;


import com.develop.prices.dto.*;
import com.develop.prices.mapper.ProductInShopRestMapper;
import com.develop.prices.mapper.ShopRestMapper;
import com.develop.prices.dto.ProductInShopDTO;
import com.develop.prices.service.ShopService;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.PageResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.develop.prices.service.ShopService;
import com.develop.prices.mapper.ShopRestMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    private final ShopRestMapper shopRestMapper;

    @Autowired
    public ShopController(ShopService shopService, ShopRestMapper shopRestMapper) {
        this.shopService = shopService;
        this.shopRestMapper = shopRestMapper;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<ShopDTO>> getShopLocationWithFilters(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @PageableDefault(sort = "shopId", direction = Sort.Direction.ASC) Pageable pageable) {

        PageResponse<ShopTo> shopTo = shopService.findAllShopWithFilters(country, city, address, pageable);

        List<ShopDTO> shopDTOList = shopTo.getContent()
                .stream()
                .map(shopRestMapper::toShopDTO)
                .toList();

        PageResponse<ShopDTO> shopDTOPageResponse = new PageResponse<>(
                shopDTOList,
                shopTo.getTotalElements(),
                shopTo.getTotalPages()
        );

        return ResponseEntity.ok(shopDTOPageResponse);

    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable(required = false) Integer shopId){
        ShopTo shopTo = shopService.findShopById(shopId);

        if(shopTo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopDTO shopDTO = shopRestMapper.toShopDTO(shopTo);

        return ResponseEntity.ok(shopDTO);
    }

//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "Created",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"shopId\": 4, \"country\": \"España\", \"city\": \"Coruña\", \"address\": \"Os Mallos 10\" }"
//                            )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Missing or invalid fields",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Missing required field: city\" }"
//                            )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "409",
//                    description = "Shop already exists",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Shop already exists at this address in this city and country\" }"
//                            )
//                    )
//            )
//    })
//    @PostMapping("")
//    public ResponseEntity<ShopDTO> addShop(@Valid @RequestBody ShopAddDTO shopAddDTO) {
//
//        ShopModel newShopModel = new ShopModel();
//
//        newShopModel.setCountry(shopAddDTO.getCountry());
//        newShopModel.setCity(shopAddDTO.getCity());
//        newShopModel.setAddress(shopAddDTO.getAddress());
//
//        ShopModel shopModel = shopLocationRepository.save(newShopModel);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(shopMapper.shopModelToShopDTO(shopModel));
//    }
//
//
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Product added to shop",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"productId\": 1, \"shopId\": 2, \"price\": 15.99 }"
//                            )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid price provided",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Price must be greater than or equal to 0\" }"
//                            )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Shop or product not found",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Product or shop not found\" }"
//                            )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "409",
//                    description = "Product already exists in shop",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"This product is already registered in this shop\" }"
//                            )
//                    )
//            )
//    })
//    @PostMapping("/{shopId}/products/{productId}")
//    public ResponseEntity<ProductInShopDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId, @Valid @RequestBody AddProductShopDTO addProductShopDTO) {
//
//        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
//        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
//
//        if (optionalProductModel.isEmpty() || optionalShopModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        if (productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//
//        BigDecimal price = addProductShopDTO.getPrice();
//
//        ProductInShopModel productInShopModel = buildProductInShopModel(optionalProductModel.get(), optionalShopModel.get(), price);
//
//
//        ProductInShopModel productInShopModelDB = productInShopRepository.save(productInShopModel);
//
//
//        return ResponseEntity.ok(productInShopMapper.productInShopModelToProductInShopDTO(productInShopModelDB));
//
//    }
//    @PutMapping("/{shopId}")
//    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer shopId, @Valid @RequestBody UpdateShopDTO updateShopDTO) {
//
//        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
//        if (optionalShopModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        ShopModel shopModel = optionalShopModel.get();
//        shopModel.setCountry(updateShopDTO.getCountry());
//        shopModel.setCity(updateShopDTO.getCity());
//        shopModel.setAddress(updateShopDTO.getAddress());
//
//        ShopModel saveShopModel = shopLocationRepository.save(shopModel);
//
//        return ResponseEntity.ok(shopMapper.shopModelToShopDTO(saveShopModel));
//    }
//
//    @PatchMapping("/{shopId}")
//    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId, @Valid @RequestBody UpdateShopDTO updateShopDTO) {
//        // Primero verificar si la tienda existe
//        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
//        if (optionalShopModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        ShopModel shopModel = optionalShopModel.get();
//
//
//        if (updateShopDTO.getCountry() != null) {
//            shopModel.setCountry(updateShopDTO.getCountry());
//        }
//
//        if (updateShopDTO.getCity() != null) {
//
//            shopModel.setCity(updateShopDTO.getCity());
//        }
//
//        if (updateShopDTO.getAddress() != null) {
//
//            shopModel.setAddress(updateShopDTO.getAddress());
//        }
//
//
//        ShopModel saveShopModel = shopLocationRepository.save(shopModel);
//        return ResponseEntity.ok(shopMapper.shopModelToShopDTO(saveShopModel));
//    }
//
//    @PatchMapping("/{shopId}/products/{productId}")
//    public ResponseEntity<ProductInShopDTO> updateProductInShop(@PathVariable Integer shopId, @PathVariable Integer productId, @Valid @RequestBody ProductInShopPatchDTO productInShopPatchDTO) {
//
//        ProductInShopModel productInShopModel = productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
//        if(productInShopModel == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        BigDecimal price = productInShopPatchDTO.getPrice();
//
//        productInShopModel.setPrice(price);
//
//        ProductInShopModel savePriceModel = productInShopRepository.save(productInShopModel);
//
//        return ResponseEntity.ok(productInShopMapper.productInShopModelToProductInShopDTO(savePriceModel));
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "204",
//                    description = "The shop has been deleted successfully"
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Shop not found",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Shop not found\" }"
//                            )
//                    )
//            ),
//    })
//
//    @DeleteMapping("/{shopId}")
//    public ResponseEntity<ShopDTO> deleteShop(@PathVariable Integer shopId) {
//        if (shopLocationRepository.findById(shopId).isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        shopLocationRepository.deleteById(shopId);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "204",
//                    description = "The shop has been deleted successfully"
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Shop not found",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Shop not found\" }"
//                            )
//                    )
//            ),
//    })
//
//    @DeleteMapping("/{shopId}/products/{productId}")
//    public ResponseEntity<ProductInShopModel> deleteProductFromShop(@PathVariable Integer productId, @PathVariable Integer shopId) {
//
//        ProductInShopModel productInShopModel = productInShopRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
//        if (productInShopModel == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        shopLocationRepository.deleteById(shopId);
//
//        return ResponseEntity.ok().build();
//    }
//
//    private ProductInShopModel buildProductInShopModel(ProductModel product, ShopModel shop, BigDecimal price) {
//        ProductInShopModel productInShopModel = new ProductInShopModel();
//        productInShopModel.setProduct(product);
//        productInShopModel.setShop(shop);
//        productInShopModel.setPrice(price);
//
//        return productInShopModel;
//    }

}
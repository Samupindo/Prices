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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/shops")
public class ShopController {

    private List<ShopDTO> shopDTOS = new ArrayList<>();
    private List<ProductPriceDTO> productPriceDTOS = new ArrayList<>();
    private final ShopLocationRepository shopLocationRepository;
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    public ShopController(ShopLocationRepository shopLocationRepository, ProductRepository productRepository, ProductPriceRepository productPriceRepository) {
        this.shopLocationRepository = shopLocationRepository;
        this.productRepository = productRepository;
        this.productPriceRepository = productPriceRepository;
    }

    @GetMapping("")
    public List<ShopModel> getAllShops() {
        return shopLocationRepository.findAll(Sort.by(Sort.Direction.ASC, "shopId"));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<List<ShopDTO>> getShopLocationDTO(@PathVariable Integer shopId) {

        ShopModel shop = (ShopModel) shopLocationRepository.findById(shopId).orElse(null);
        if (shop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(List.of(new ShopDTO(
                shop.getShopId(),
                shop.getCountry(),
                shop.getCity(),
                shop.getAddress()
        )));

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
    public ResponseEntity<ShopDTO> addShop(@RequestBody ShopAddDTO newShopDTO) {

        for (ShopDTO shop : shopDTOS) {
//           Comprobación para que la calle no exista dos veces, para ello se comprueba que sea en la misma ciudad y país.

            if (shop.getCountry().equalsIgnoreCase(newShopDTO.getCountry()) && shop.getCity().equalsIgnoreCase(newShopDTO.getCity()) && shop.getAddress().equalsIgnoreCase(newShopDTO.getAddress())) {
                String message = "That field already exists";
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        ShopModel newShopLocation = new ShopModel();

        newShopLocation.setCountry(newShopDTO.getCountry());
        newShopLocation.setCity(newShopDTO.getCity());
        newShopLocation.setAddress(newShopDTO.getAddress());

        ShopModel shopModel = shopLocationRepository.save(newShopLocation);

        ShopDTO newShowDTO = new ShopDTO();

        newShowDTO.setShopId(shopModel.getShopId());
        newShowDTO.setCountry(shopModel.getCountry());
        newShowDTO.setCity(shopModel.getCity());
        newShowDTO.setAddress(shopModel.getAddress());

        return ResponseEntity.status(HttpStatus.CREATED).body(newShowDTO);
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
    public ResponseEntity<ProductPriceDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId, @RequestBody AddProductShopDTO product) {
        BigDecimal price = product.getPrice();


        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }

        if (productRepository.findById(productId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (shopLocationRepository.findById(shopId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ProductPriceModel priceModel = new ProductPriceModel();

        priceModel.setProduct(productRepository.findById(productId).get());
        priceModel.setShop(shopLocationRepository.findById(shopId).get());
        priceModel.setPrice(price);

        if (productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        productPriceRepository.save(priceModel);

        ProductPriceDTO productPriceDTO = new ProductPriceDTO();
        productPriceDTO.setShopId(priceModel.getShop().getShopId());
        productPriceDTO.setProductId(priceModel.getProduct().getProductId());
        productPriceDTO.setPrice(priceModel.getPrice());

        return ResponseEntity.ok(productPriceDTO);

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
    public ResponseEntity<ProductPriceModel> deleteProductFromShop(@PathVariable Integer productId, @PathVariable Integer shopId){

        ProductPriceModel productPriceModel =  productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId,productId).orElse(null);
        if (productPriceModel == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        shopLocationRepository.deleteById(shopId);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer shopId, @Validated @RequestBody UpdateShopDTO updateShopDTO) {

        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (updateShopDTO.getCity() == null || updateShopDTO.getCountry() == null || updateShopDTO.getAddress() == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ShopModel shopModel = optionalShopModel.get();
        shopModel.setCountry(updateShopDTO.getCountry());
        shopModel.setCity(updateShopDTO.getCity());
        shopModel.setAddress(updateShopDTO.getAddress());

        ShopModel udpateShop = shopLocationRepository.save(shopModel);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopId(udpateShop.getShopId());
        shopDTO.setCountry(udpateShop.getCountry());
        shopDTO.setCity(udpateShop.getCity());
        shopDTO.setAddress(udpateShop.getAddress());

        return ResponseEntity.ok(shopDTO);


    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId, @RequestBody UpdateShopDTO updateShopDTO) {


        Optional<ShopModel> optionalShop = shopLocationRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShop.get();

        // Validar los campos del DTO
        if (updateShopDTO.getCountry() != null && updateShopDTO.getCountry().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (updateShopDTO.getCity() != null && updateShopDTO.getCity().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (updateShopDTO.getAddress() != null && updateShopDTO.getAddress().trim().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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
        ShopModel updateShop = shopLocationRepository.save(shopModel);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopId(updateShop.getShopId());
        shopDTO.setCountry(updateShop.getCountry());
        shopDTO.setCity(updateShop.getCity());
        shopDTO.setAddress(updateShop.getAddress());

        return ResponseEntity.ok(shopDTO);

    }

    @PatchMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@PathVariable Integer shopId, @PathVariable Integer productId, @RequestBody ProductPricePatchDTO productPricePatchDTO) {
        BigDecimal price = productPricePatchDTO.getPrice();

        ProductPriceModel priceModel = productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if (priceModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (price == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        priceModel.setPrice(price);

        ProductPriceModel savePriceModel = productPriceRepository.save(priceModel);

        ProductPriceDTO productPriceDTO = new ProductPriceDTO();
        productPriceDTO.setProductId(savePriceModel.getProduct().getProductId());
        productPriceDTO.setShopId(savePriceModel.getShop().getShopId());
        productPriceDTO.setPrice(savePriceModel.getPrice());
        return ResponseEntity.ok(productPriceDTO);
    }



    @GetMapping("/filter")
    public ResponseEntity<List<ShopModel>> getShopLocationWithFilters(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address) {

        List<ShopModel> shops = new ArrayList<>();

        if(country!=null){
            shops = shopLocationRepository.findAll(ShopsSpecification.findByCountry(country));
        }

        if(city!=null){
            shops = shopLocationRepository.findAll(ShopsSpecification.findByCity(city));
        }

        if(address!=null){
            shops = shopLocationRepository.findAll(ShopsSpecification.findByAddress(address));
        }


        return ResponseEntity.ok(shops);
    }


}


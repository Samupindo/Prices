package com.develop.prices.controller;


import com.develop.prices.modelo.dto.*;
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
public class ShopController {

    private List<ShopLocationDTO> shopLocationDTOS = new ArrayList<>();
    private List<ProductPriceDTO> productPriceDTOS = new ArrayList<>();


    public ShopController() {


    }

    @GetMapping("/shops")
    public List<ShopLocationDTO> getAllShops() {
        return shopLocationDTOS;
    }

    @GetMapping("/shop/{shopId}")
    public List<ShopLocationDTO> getShopLocationDTO(@PathVariable Integer shopId) {

        for (ShopLocationDTO shopLocationDTO : shopLocationDTOS) {
            if (shopLocationDTO.getShopId().equals(shopId)) {
                return List.of(new ShopLocationDTO(
                        shopLocationDTO.getShopId(),
                        shopLocationDTO.getCountry(),
                        shopLocationDTO.getCity(),
                        shopLocationDTO.getAddress()
                ));
            }
        }

        return Collections.emptyList();  // Devuelve una lista vacía si no se encuentra la tienda
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
    @PostMapping("/shop")
    public ResponseEntity<ShopLocationDTO> addShop(@RequestBody ShopAddDTO newShopDTO) {

        ShopLocationDTO newShopLocation = new ShopLocationDTO();

        for (ShopLocationDTO shop : shopLocationDTOS) {
//           Comprobación para que la calle no exista dos veces,para ello se comprueba que sea en la misma ciudad y país.

            if (shop.getCountry().equalsIgnoreCase(newShopDTO.getCountry()) && shop.getCity().equalsIgnoreCase(newShopDTO.getCity()) && shop.getAddress().equalsIgnoreCase(newShopDTO.getAddress())) {
                String message = "That field already exists";
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        newShopLocation.setCountry(newShopDTO.getCountry());
        newShopLocation.setCity(newShopDTO.getCity());
        newShopLocation.setAddress(newShopDTO.getAddress());
        shopLocationDTOS.add(newShopLocation);

        return ResponseEntity.status(HttpStatus.CREATED).body(newShopLocation);
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
    @PostMapping("/shop/{shopId}/addProduct/{productId}")
    public ResponseEntity<ProductPriceDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId, @RequestBody AddProductShopDTO product) {
        BigDecimal price = product.getPrice();


        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }

        boolean productexists = false;
        for (ProductPriceDTO productsList : productPriceDTOS) {
            if (productsList.getProductId().equals(productId)) {
                productexists = true;
                break;
            }
        }

        if (!productexists) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean shopexists = false;
        for (ShopLocationDTO shopLocationDTO : shopLocationDTOS){
            shopexists = true;
            break;
        }

        if(!shopexists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        for (ProductPriceDTO existingProduct : productPriceDTOS) {
            if (productId.equals(existingProduct.getProductId()) && shopId.equals(existingProduct.getShopId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        for (ShopLocationDTO currentShop : shopLocationDTOS) {
            if (currentShop.getShopId().equals(shopId)) {
                ProductPriceDTO newProduct = new ProductPriceDTO();
                newProduct.setProductId(productId);
                newProduct.setShopId(shopId);
                newProduct.setPrice(price);

                productPriceDTOS.add(newProduct);

                return ResponseEntity.ok(newProduct);
            }
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/shop/{shopId}")
    public ResponseEntity<ShopLocationDTO> deleteShop(@PathVariable Integer shopId) {
        boolean removed = shopLocationDTOS.removeIf(shop -> shop.getShopId().equals(shopId));

        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/shop/{shopId}")
    public ResponseEntity<ShopLocationDTO> updateShop(@PathVariable Integer shopId, @Validated @RequestBody UpdateShopDTO updateShopDTO) {
        for (int i = 0; i < shopLocationDTOS.size(); i++) {
            ShopLocationDTO currentShop = shopLocationDTOS.get(i);

            if (currentShop.getShopId().equals(shopId)) {

                if (updateShopDTO.getCountry() == null ||
                        updateShopDTO.getCity() == null || updateShopDTO.getAddress() == null) {
//                    return ResponseEntity.badRequest().body("The fields cannot be empty");
                    return ResponseEntity.badRequest().build();
                }
                currentShop.setCountry(updateShopDTO.getCountry());
                currentShop.setCity(updateShopDTO.getCity());
                currentShop.setAddress(updateShopDTO.getAddress());

                return ResponseEntity.ok(currentShop);
            }
        }
        return ResponseEntity.status(404).build();

    }

    @PatchMapping("/shop/{shopId}")
    public ResponseEntity<ShopLocationDTO> partialUpdateShop(@PathVariable Integer shopId, @RequestBody UpdateShopDTO updateShopDTO) {
        ShopLocationDTO shopLocation = null;
        for (ShopLocationDTO shop : shopLocationDTOS) {
            if (shop.getShopId().equals(shopId)) {
                shopLocation = shop;
                break;
            }
        }

        if (shopLocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Validar los campos del DTO
        if (updateShopDTO.getCountry() != null && updateShopDTO.getCountry().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Country cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (updateShopDTO.getCity() != null && updateShopDTO.getCity().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (updateShopDTO.getAddress() != null && updateShopDTO.getAddress().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Actualizar solo los campos no nulos
        if (updateShopDTO.getCountry() != null) {
            shopLocation.setCountry(updateShopDTO.getCountry());
        }
        if (updateShopDTO.getCity() != null) {
            shopLocation.setCity(updateShopDTO.getCity());
        }
        if (updateShopDTO.getAddress() != null) {
            shopLocation.setAddress(updateShopDTO.getAddress());
        }

        return ResponseEntity.ok(shopLocation);
    }

    @PatchMapping("/shop/{shopId}/product/{productId}")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@PathVariable Integer shopId, @PathVariable Integer productId, @RequestBody ProductPricePatchDTO productPricePatchDTO) {

        // Verificar si el precio está presente en el DTO
        if (productPricePatchDTO.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Buscar la tienda por shopId
        ShopLocationDTO shopLocation = shopLocationDTOS.stream()
                .filter(shop -> shop.getShopId().equals(shopId))
                .findFirst()
                .orElse(null);

        if (shopLocation == null) {
            // Si no se encuentra la tienda, retornar NOT_FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Buscar el producto y actualizar su precio
        for (ProductPriceDTO product : productPriceDTOS) {
            if (product.getProductId().equals(productId) && product.getShopId().equals(shopId)) {
                product.setPrice(productPricePatchDTO.getPrice());  // Actualizar el precio
                return ResponseEntity.ok(product);  // Retornar el producto actualizado
            }
        }

        // Si no se encuentra el producto, retornar NOT_FOUND
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/shop/filter")
    public ResponseEntity<List<ShopLocationDTO>> getShopLocationWithFilters(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address) {

        List<ShopLocationDTO> shops = new ArrayList<>(shopLocationDTOS);
        if(city == null && country == null && address == null){
            return ResponseEntity.badRequest().build();
        }

        if (country != null) {
            shops.removeIf(shop -> !shop.getCountry().toLowerCase().contains(country.toLowerCase()));

        }
        if (city != null) {
            shops.removeIf(shop -> !shop.getCity().toLowerCase().contains(city.toLowerCase()));
        }

        if (address != null) {
            shops.removeIf(shop -> !shop.getAddress().toLowerCase().contains(address.toLowerCase()));
        }

        if(shops.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shops);
    }


}


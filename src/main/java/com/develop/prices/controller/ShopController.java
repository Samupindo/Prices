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
@RequestMapping("/shops")
public class ShopController {

    private List<ShopDTO> shopDTOS = new ArrayList<>();
    private List<ProductPriceDTO> productPriceDTOS = new ArrayList<>();


    public ShopController() {

        shopDTOS.add(new ShopDTO("España", "A Coruña", "Los Mallos, 10"));
        shopDTOS.add(new ShopDTO("Argentina", "Buenos Aires", "Dirección inventada"));
        shopDTOS.add(new ShopDTO("España", "Santiago", "Av. Toledo"));

        productPriceDTOS.add(new ProductPriceDTO(1,1, new BigDecimal("25.50")));
        productPriceDTOS.add(new ProductPriceDTO(2,2, new BigDecimal("25.50")));
        productPriceDTOS.add(new ProductPriceDTO(3,3, new BigDecimal("15.00")));
    }

    @GetMapping("")
    public List<ShopDTO> getAllShops() {
        return shopDTOS;
    }

    @GetMapping("/{shopId}")
    public List<ShopDTO> getShopLocationDTO(@PathVariable Integer shopId) {

        for (ShopDTO shopDTO : shopDTOS) {
            if (shopDTO.getShopId().equals(shopId)) {
                return List.of(new ShopDTO(
                        shopDTO.getShopId(),
                        shopDTO.getCountry(),
                        shopDTO.getCity(),
                        shopDTO.getAddress()
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
    @PostMapping("")
    public ResponseEntity<ShopDTO> addShop(@RequestBody ShopAddDTO newShopDTO) {

        ShopDTO newShopLocation = new ShopDTO();

        for (ShopDTO shop : shopDTOS) {
//           Comprobación para que la calle no exista dos veces,para ello se comprueba que sea en la misma ciudad y país.

            if (shop.getCountry().equalsIgnoreCase(newShopDTO.getCountry()) && shop.getCity().equalsIgnoreCase(newShopDTO.getCity()) && shop.getAddress().equalsIgnoreCase(newShopDTO.getAddress())) {
                String message = "That field already exists";
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        newShopLocation.setCountry(newShopDTO.getCountry());
        newShopLocation.setCity(newShopDTO.getCity());
        newShopLocation.setAddress(newShopDTO.getAddress());
        shopDTOS.add(newShopLocation);

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
    @PostMapping("/{shopId}/addProduct/{productId}")
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
        for (ShopDTO shopDTO : shopDTOS){
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

        for (ShopDTO currentShop : shopDTOS) {
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

    @DeleteMapping("/{shopId}")
    public ResponseEntity<ShopDTO> deleteShop(@PathVariable Integer shopId) {
        boolean removed = shopDTOS.removeIf(shop -> shop.getShopId().equals(shopId));

        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "204",
                    description = "The shop has been deleted successfully"
            )
    })

    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer shopId, @Validated @RequestBody UpdateShopDTO updateShopDTO) {
        for (int i = 0; i < shopDTOS.size(); i++) {
            ShopDTO currentShop = shopDTOS.get(i);

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

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId, @RequestBody UpdateShopDTO updateShopDTO) {
        ShopDTO shopLocation = null;
        for (ShopDTO shop : shopDTOS) {
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

    @PatchMapping("/{shopId}/product/{productId}")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@PathVariable Integer shopId, @PathVariable Integer productId, @RequestBody ProductPricePatchDTO productPricePatchDTO) {

        // Verificar si el precio está presente en el DTO
        if (productPricePatchDTO.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Buscar la tienda por shopId
        ShopDTO shopLocation = shopDTOS.stream()
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


    @GetMapping("/filter")
    public ResponseEntity<List<ShopDTO>> getShopLocationWithFilters(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address) {

        List<ShopDTO> shops = new ArrayList<>(shopDTOS);
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


package com.develop.prices.controller;


import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.model.dto.ShopAddDTO;
import com.develop.prices.model.dto.ShopDTO;
import com.develop.prices.model.dto.UpdateShopDTO;
import com.develop.prices.model.dto.PageResponse;
import com.develop.prices.model.dto.ProductPriceDTO;
import com.develop.prices.model.dto.AddProductShopDTO;
import com.develop.prices.model.dto.ProductPricePatchDTO;
import com.develop.prices.repository.ShopLocationRepository;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.repository.ShopsSpecification;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

        for (ShopDTO shop : shopDTOS) {
//           Comprobación para que la calle no exista dos veces, para ello se comprueba que sea en la misma ciudad y país.

            if (shop.getCountry().equalsIgnoreCase(shopAddDTO.getCountry()) && shop.getCity().equalsIgnoreCase(shopAddDTO.getCity()) && shop.getAddress().equalsIgnoreCase(shopAddDTO.getAddress())) {
                String message = "That field already exists";
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
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
    public ResponseEntity<ProductPriceDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId,@Validated @RequestBody AddProductShopDTO addProductShopDTO) {
        BigDecimal price = addProductShopDTO.getPrice();
        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }

        if (optionalProductModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ProductPriceModel productPriceModel = buildProductPriceModel(optionalProductModel.get(), optionalShopModel.get(), addProductShopDTO.getPrice());

        if (productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        productPriceRepository.save(productPriceModel);


        return ResponseEntity.ok(toProductPriceDTO(productPriceModel));

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

        ShopModel saveShopModel1 = shopLocationRepository.save(shopModel);

        return ResponseEntity.ok(toShopDTO(saveShopModel1));
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId,@Validated @RequestBody UpdateShopDTO updateShopDTO) {


        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShopModel.get();

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
        ShopModel saveShopModel = shopLocationRepository.save(shopModel);

        return ResponseEntity.ok(toShopDTO(saveShopModel));

    }

    @PatchMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@PathVariable Integer shopId, @PathVariable Integer productId,@Validated @RequestBody ProductPricePatchDTO productPricePatchDTO) {
        BigDecimal price = productPricePatchDTO.getPrice();

        ProductPriceModel productPriceModel = productPriceRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if (productPriceModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (price == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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

        if(country!=null){
            spec = spec.and(ShopsSpecification.findByCountry(country));
        }

        if(city!=null){
            spec = spec.and(ShopsSpecification.findByCity(city));
        }

        if(address!=null){
            spec = spec.and(ShopsSpecification.findByAddress(address));
        }


        Page<ShopModel> shopModelPage = shopLocationRepository.findAll(spec, pageable);
        List<ShopDTO> shopDTOList = shopModelPage.getContent()
                .stream()
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

    private ShopDTO toShopDTO (ShopModel shopModel){
        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setShopId(shopModel.getShopId());
        shopDTO.setCountry(shopModel.getCountry());
        shopDTO.setCity(shopModel.getCity());
        shopDTO.setAddress(shopModel.getAddress());

        return shopDTO;
    }
}


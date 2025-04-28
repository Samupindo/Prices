package com.develop.prices.controller;


import com.develop.prices.dto.*;
import com.develop.prices.mapper.ShopProductInfoMapper;
import com.develop.prices.mapper.ShopMapper;
import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ShopProductInfoModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.dto.ShopProductInfoDTO;
import com.develop.prices.repository.ShopLocationRepository;
import com.develop.prices.repository.ShopProductInfoRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.specification.ShopsSpecification;
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
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/shops")
public class ShopController {

    private final ShopLocationRepository shopLocationRepository;
    private final ProductRepository productRepository;
    private final ShopProductInfoRepository shopProductInfoRepository;
    private final ShopMapper shopMapper;
    private final ShopProductInfoMapper shopProductInfoMapper;

    public ShopController(ShopLocationRepository shopLocationRepository, ProductRepository productRepository, ShopProductInfoRepository shopProductInfoRepository, ShopMapper shopMapper, ShopProductInfoMapper shopProductInfoMapper) {
        this.shopLocationRepository = shopLocationRepository;
        this.productRepository = productRepository;
        this.shopProductInfoRepository = shopProductInfoRepository;
        this.shopMapper = shopMapper;
        this.shopProductInfoMapper = shopProductInfoMapper;
    }

    @GetMapping("")
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
                //.map(s -> toShopDTO(s))
                .map(shopMapper::shopModelToShopDTO)
                .toList();

        PageResponse<ShopDTO> shopDTOPageResponse = new PageResponse<>(
                shopDTOList,
                shopModelPage.getTotalElements(),
                shopModelPage.getTotalPages()
        );

        return ResponseEntity.ok(shopDTOPageResponse);

    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable(required = false) Integer shopId){

        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

        if(optionalShopModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShopModel.get();
        ShopDTO shopDTO = shopMapper.shopModelToShopDTO(shopModel);

        return ResponseEntity.ok(shopDTO);
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
    public ResponseEntity<ShopDTO> addShop(@Valid @RequestBody ShopAddDTO shopAddDTO) {

        ShopModel newShopModel = new ShopModel();

        newShopModel.setCountry(shopAddDTO.getCountry());
        newShopModel.setCity(shopAddDTO.getCity());
        newShopModel.setAddress(shopAddDTO.getAddress());

        ShopModel shopModel = shopLocationRepository.save(newShopModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(shopMapper.shopModelToShopDTO(shopModel));
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
    public ResponseEntity<ShopProductInfoDTO> addProductShop(@PathVariable Integer productId, @PathVariable Integer shopId, @Valid @RequestBody AddProductShopDTO addProductShopDTO) {

        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

        if (optionalProductModel.isEmpty() || optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (shopProductInfoRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        BigDecimal price = addProductShopDTO.getPrice();

        ShopProductInfoModel shopProductInfoModel = buildShopProductInfoModel(optionalProductModel.get(), optionalShopModel.get(), price);


        ShopProductInfoModel shopProductInfoModelDB = shopProductInfoRepository.save(shopProductInfoModel);


        return ResponseEntity.ok(shopProductInfoMapper.shopProductInfoModelToShopProductInfoDTO(shopProductInfoModelDB));

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
    public ResponseEntity<ShopProductInfoModel> deleteProductFromShop(@PathVariable Integer productId, @PathVariable Integer shopId) {

        ShopProductInfoModel shopProductInfoModel = shopProductInfoRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if (shopProductInfoModel == null) {
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

        return ResponseEntity.ok(shopMapper.shopModelToShopDTO(saveShopModel));
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDTO> partialUpdateShop(@PathVariable Integer shopId, @Valid @RequestBody UpdateShopDTO updateShopDTO) {
        // Primero verificar si la tienda existe
        Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);
        if (optionalShopModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShopModel shopModel = optionalShopModel.get();


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
        return ResponseEntity.ok(shopMapper.shopModelToShopDTO(saveShopModel));
    }

    @PatchMapping("/{shopId}/products/{productId}")
    public ResponseEntity<ShopProductInfoDTO> updateShopProductInfo(@PathVariable Integer shopId, @PathVariable Integer productId, @Valid @RequestBody ShopProductInfoPatchDTO shopProductInfoPatchDTO) {

        ShopProductInfoModel shopProductInfoModel = shopProductInfoRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId).orElse(null);
        if(shopProductInfoModel == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        BigDecimal price = shopProductInfoPatchDTO.getPrice();

        shopProductInfoModel.setPrice(price);

        ShopProductInfoModel savePriceModel = shopProductInfoRepository.save(shopProductInfoModel);

        return ResponseEntity.ok(shopProductInfoMapper.shopProductInfoModelToShopProductInfoDTO(savePriceModel));
    }

    private ShopProductInfoModel buildShopProductInfoModel(ProductModel product, ShopModel shop, BigDecimal price) {
        ShopProductInfoModel shopProductInfoModel = new ShopProductInfoModel();
        shopProductInfoModel.setProduct(product);
        shopProductInfoModel.setShop(shop);
        shopProductInfoModel.setPrice(price);

        return shopProductInfoModel;
    }

}
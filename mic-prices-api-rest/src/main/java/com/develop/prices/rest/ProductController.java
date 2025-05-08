//package com.develop.prices.rest;
//
//import com.develop.prices.dto.*;
//import com.develop.prices.entity.ProductInShopModel;
//import com.develop.prices.entity.ProductModel;
//import com.develop.prices.dto.ProductWithShopsDTO;
//import com.develop.prices.mapper.ProductRestMapper;
//import com.develop.prices.service.ProductService;
//import com.develop.prices.specification.ProductInShopSpecification;
//import com.develop.prices.repository.ProductRepository;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//@RestController
//public class ProductController {
//    private final ProductService productService;
//
//    private final ProductRestMapper productRestMapper;
//
//    @Autowired
//    public ProductController(ProductService productService, ProductRestMapper productRestMapper) {
//        this.productService = productService;
//        this.productRestMapper = productRestMapper;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<PageResponse<ProductWithShopsDTO>> getProductsWithFilters(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) BigDecimal priceMin,
//            @RequestParam(required = false) BigDecimal priceMax,
//            @PageableDefault(sort = "productId", direction = Sort.Direction.ASC) Pageable pageable) {
//
//        Specification<ProductModel> spec = Specification.where(null);
//
//        if (name != null && !name.isBlank()) {
//            spec = spec.and(ProductInShopSpecification.hasName(name));
//        }
//
//        if (priceMin != null) {
//            spec = spec.and(ProductInShopSpecification.hasPriceMin(priceMin));
//        }
//
//        if (priceMax != null) {
//            spec = spec.and(ProductInShopSpecification.hasPriceMax(priceMax));
//        }
//
//        Page<ProductModel> productModelPage = productRepository.findAll(spec, pageable);
//        List<ProductWithShopsDTO> productWithShopsDTOList = new ArrayList<>();
//
//        for (ProductModel product : productModelPage.getContent()) {
//            List<ShopInfoDTO> shops = new ArrayList<>();
//
//            if (product.getPrices() != null) {
//                for (ProductInShopModel price : product.getPrices()) {
//                    if ((priceMin == null || price.getPrice().compareTo(priceMin) >= 0) &&
//                            (priceMax == null || price.getPrice().compareTo(priceMax) <= 0)) {
//
//                        shops.add(new ShopInfoDTO(price.getProductInShopId(),price.getShop().getShopId(), price.getPrice()));
//                    }
//                }
//            }
//
//            productWithShopsDTOList.add(new ProductWithShopsDTO(
//                    product.getProductId(),
//                    product.getName(),
//                    shops
//            ));
//        }
//
//        PageResponse<ProductWithShopsDTO> pageResponse = new PageResponse<>(
//                productWithShopsDTOList,
//                productModelPage.getTotalElements(),
//                productModelPage.getTotalPages()
//        );
//
//        return ResponseEntity.ok(pageResponse);
//
//    }
//
//    @GetMapping("/{productId}")
//    public ResponseEntity<ProductWithShopsDTO> getProductById(@PathVariable Integer productId){
//        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
//        if(optionalProductModel.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        ProductModel productModel = optionalProductModel.get();
//
//        ProductWithShopsDTO productWithShopsDTO = new ProductWithShopsDTO();
//        productWithShopsDTO.setProductId(productModel.getProductId());
//        productWithShopsDTO.setName(productModel.getName());
//
//        List<ShopInfoDTO> shopInfoDTOList = new ArrayList<>();
//
//        for (ProductInShopModel productInShopModel : productModel.getPrices()){
//            shopInfoDTOList.add(new ShopInfoDTO(productInShopModel.getProductInShopId(), productInShopModel.getShop().getShopId(), productInShopModel.getPrice()));
//        }
//
//        productWithShopsDTO.setShop(shopInfoDTOList);
//
//        return ResponseEntity.ok(productWithShopsDTO);
//    }
//
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "Created",
//                    content = @Content(mediaType = "application/json")
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid input",
//                    content = @Content(mediaType = "application/json",
//                            examples = @ExampleObject(
//                                    value = "{ \"error\": \"Missing required field: name\" }"
//                            )
//                    )
//            )
//    })
//    @PostMapping("")
//    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductNameDTO productNameDTO) {
//        // Crear nuevo producto
//        ProductModel productModel = new ProductModel();
//        productModel.setName(productNameDTO.getName());
//
//        // Guardar en base de datos
//        ProductModel savedProduct = productRepository.save(productModel);
//
//        // Devolver DTO como respuesta
//        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toProductDTO(savedProduct));
//    }
//
//    @PutMapping("/{productId}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductNameDTO productNameDTO) {
//        ProductModel productModel = productRepository.findById(productId).orElse(null);
//        if (productModel == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        productModel.setName(productNameDTO.getName());
//        return ResponseEntity.ok(productMapper.toProductDTO(productModel));
//    }
//
//
//    @DeleteMapping("/{productId}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
//        if (productRepository.existsById(productId)) {
//            productRepository.deleteById(productId);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//
//
//}

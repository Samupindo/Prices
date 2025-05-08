//package com.develop.prices.rest;
//
//import com.develop.prices.dto.PageResponse;
//import com.develop.prices.dto.PostPurchaseDTO;
//import com.develop.prices.dto.ProductInShopDTO;
//import com.develop.prices.dto.PurchaseDTO;
//import com.develop.prices.entity.CustomerModel;
//import com.develop.prices.entity.ProductInShopModel;
//import com.develop.prices.entity.PurchaseModel;
//import com.develop.prices.entity.PurchaseLineModel;
//import com.develop.prices.repository.CustomerRepository;
//import com.develop.prices.repository.ProductInShopRepository;
//import com.develop.prices.repository.PurchaseRepository;
//import com.develop.prices.specification.PurchaseSpecification;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.validation.Valid;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//
//public class PurchaseController {
//    private final PurchaseRepository purchaseRepository;
//    private final ProductInShopRepository productInShopRepository;
//    private final PurchaseMapper purchaseMapper;
//    private final ProductInShopMapper productInShopMapper;
//    private final CustomerRepository customerRepository;
//
//    public PurchaseController(PurchaseRepository purchaseRepository, ProductInShopRepository productInShopRepository, PurchaseMapper purchaseMapper, ProductInShopMapper productInShopMapper, CustomerRepository customerRepository) {
//        this.purchaseRepository = purchaseRepository;
//        this.productInShopRepository = productInShopRepository;
//        this.purchaseMapper = purchaseMapper;
//        this.productInShopMapper = productInShopMapper;
//        this.customerRepository = customerRepository;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<PageResponse<PurchaseDTO>> getPurchasesWithFilters(
//            @RequestParam(required = false) Integer customerId,
//            @RequestParam(required = false) List<Integer> productInShop,
//            @RequestParam(required = false) BigDecimal totalPriceMax,
//            @RequestParam(required = false) BigDecimal totalPriceMin,
//            @RequestParam(required = false) Boolean shopping,
//            @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {
//
//
//        Specification<PurchaseModel> spec = Specification.where(null);
//
//        if (customerId != null) {
//            spec = spec.and(PurchaseSpecification.hasCustomer(customerId));
//        }
//
//        if (productInShop != null && !productInShop.isEmpty()) {
//            spec = spec.and(PurchaseSpecification.hasProductInShop(productInShop));
//
//        }
//        if(totalPriceMax != null){
//            spec =spec.and(PurchaseSpecification.hasPriceMax(totalPriceMax));
//        }
//
//        if(totalPriceMin != null){
//            spec =spec.and(PurchaseSpecification.hasPriceMin(totalPriceMin));
//        }
//
//        if(shopping != null){
//            spec = spec.and(PurchaseSpecification.hasShoppingStatus(shopping));
//        }
//
//        Page<PurchaseModel> purchasePage = purchaseRepository.findAll(spec,pageable);
//
//        List<PurchaseDTO> purchaseDTOList = purchasePage.getContent()
//                .stream()
//                .map(purchase -> {
//                    PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(purchase);
//                    List<ProductInShopDTO> productDTOs = purchase.getPurchaseLineModels().stream()
//                            .map(p -> productInShopMapper.productInShopModelToProductInShopDTO(p.getProductInShop()))
//                            .collect(Collectors.toList());
//                    purchaseDTO.setProducts(productDTOs);
//                    return purchaseDTO;
//                })
//                .collect(Collectors.toList());
//
//
//        PageResponse<PurchaseDTO> pageResponse = new PageResponse<>(
//                purchaseDTOList,
//                purchasePage.getTotalElements(),
//                purchasePage.getTotalPages()
//        );
//        return ResponseEntity.ok(pageResponse);
//
//    }
//
//
//    @GetMapping("/{purchaseId}")
//    public ResponseEntity<PurchaseDTO> getPurchaseById(@PathVariable Integer purchaseId){
//        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
//
//        if (optionalPurchaseModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        PurchaseModel purchaseModel = optionalPurchaseModel.get();
//
//        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(purchaseModel);
//
//        List<ProductInShopDTO> productDTOs = purchaseModel.getPurchaseLineModels().stream()
//                .map(p -> productInShopMapper.productInShopModelToProductInShopDTO(p.getProductInShop()))
//                .collect(Collectors.toList());
//        purchaseDTO.setProducts(productDTOs);
//
//        return ResponseEntity.ok(purchaseDTO);
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
//
//    @PostMapping("")
//    public ResponseEntity<PurchaseDTO> postPurchase(@Valid @RequestBody PostPurchaseDTO postPurchaseDTO) {
//        CustomerModel customerModel = customerRepository.findById(postPurchaseDTO.getCustomerId()).orElse(null);
//
//        if(customerModel == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        PurchaseModel purchaseModel =new PurchaseModel();
//        purchaseModel.setCustomer(customerModel);
//        purchaseModel.setTotalPrice(BigDecimal.ZERO);
//        purchaseModel.setShopping(true);
//
//        PurchaseModel savedPurchaseModel = purchaseRepository.save(purchaseModel);
//
//        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(savedPurchaseModel);
//        purchaseDTO.setProducts(List.of());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDTO);
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
//
//
//    @PostMapping("/{purchaseId}/productInShop/{productInShopId}")
//    public ResponseEntity<PurchaseDTO> addProductPurchase(@PathVariable Integer purchaseId, @PathVariable Integer productInShopId) {
//        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
//        Optional<ProductInShopModel> optionalProductInShopModel = productInShopRepository.findById(productInShopId);
//
//        if (optionalPurchaseModel.isEmpty() || optionalProductInShopModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        PurchaseModel purchaseModel = optionalPurchaseModel.get();
//
//        if (!purchaseModel.isShopping()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//        ProductInShopModel productInShopModel = optionalProductInShopModel.get();
//
//        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
//        purchaseLineModel.setPurchase(purchaseModel);
//        purchaseLineModel.setProductInShop(productInShopModel);
//
//        purchaseModel.getPurchaseLineModels().add(purchaseLineModel);
//
//        purchaseModel.setTotalPrice(purchaseModel.getTotalPrice());
//
//        PurchaseModel purchaseModelDB = purchaseRepository.save(purchaseModel);
//
//
//        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(purchaseModelDB);
//
//        List<ProductInShopDTO> productDTOs = purchaseModelDB.getPurchaseLineModels().stream()
//                .map(p -> productInShopMapper.productInShopModelToProductInShopDTO(p.getProductInShop()))
//                .collect(Collectors.toList());
//        purchaseDTO.setProducts(productDTOs);
//
//        return ResponseEntity.ok(purchaseDTO);
//    }
//
//    @PatchMapping("/{purchaseId}/finish")
//    public ResponseEntity<PurchaseDTO> finishPurchase(@PathVariable Integer purchaseId) {
//        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
//
//        if (optionalPurchaseModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        PurchaseModel purchaseModel = optionalPurchaseModel.get();
//
//        if (!purchaseModel.isShopping()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//        purchaseModel.setShopping(false);
//
//        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(purchaseModel);
//
//        List<ProductInShopDTO> productDTOs = purchaseModel.getPurchaseLineModels().stream()
//                .map(p -> productInShopMapper.productInShopModelToProductInShopDTO(p.getProductInShop()))
//                .collect(Collectors.toList());
//        purchaseDTO.setProducts(productDTOs);
//
//        return ResponseEntity.ok(purchaseDTO);
//    }
//
//
//    @DeleteMapping("{purchaseId}")
//    public ResponseEntity<Void> deletePurchase(@PathVariable Integer purchaseId) {
//        if (purchaseRepository.existsById(purchaseId)) {
//            purchaseRepository.deleteById(purchaseId);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    @DeleteMapping("/{purchaseId}/productInShop/{productInShopId}")
//    public ResponseEntity<Void> deleteProductPurchase(@PathVariable Integer purchaseId, @PathVariable Integer productInShopId){
//        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
//
//        if (optionalPurchaseModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        PurchaseModel purchaseModel = optionalPurchaseModel.get();
//
//        List<PurchaseLineModel> purchaseLineModels = purchaseModel.getPurchaseLineModels();
//        if(purchaseLineModels.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        if (!purchaseModel.isShopping()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//        boolean productoExiste = purchaseModel.getPurchaseLineModels().stream()
//                .anyMatch(purchaseLine ->
//                        purchaseLine.getProductInShop().getProductInShopId().equals(productInShopId)
//                );
//
//        if (!productoExiste) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        purchaseModel.getPurchaseLineModels().removeIf(purchaseLine ->
//                purchaseLine.getProductInShop().getProductInShopId().equals(productInShopId)
//        );
//
//        purchaseRepository.save(purchaseModel);
//
//        return ResponseEntity.ok().build();
//
//    }
//
//
//
//}

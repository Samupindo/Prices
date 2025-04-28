package com.develop.prices.controller;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.mapper.ShopProductInfoMapper;
import com.develop.prices.mapper.PurchaseMapper;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ShopProductInfoModel;
import com.develop.prices.model.PurchaseModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.ShopProductInfoRepository;
import com.develop.prices.repository.PurchaseRepository;
import com.develop.prices.specification.PurchaseSpecification;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseRepository purchaseRepository;
    private final ShopProductInfoRepository shopProductInfoRepository;
    private final PurchaseMapper purchaseMapper;
    private final ShopProductInfoMapper shopProductInfoMapper;
    private final CustomerRepository customerRepository;

    public PurchaseController(PurchaseRepository purchaseRepository, ShopProductInfoRepository shopProductInfoRepository, PurchaseMapper purchaseMapper, ShopProductInfoMapper shopProductInfoMapper, CustomerRepository customerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.shopProductInfoRepository = shopProductInfoRepository;
        this.purchaseMapper = purchaseMapper;
        this.shopProductInfoMapper = shopProductInfoMapper;
        this.customerRepository = customerRepository;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<PurchaseDTO>> getPurchasesWithFilters(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) List<ShopProductInfoModel> info,
            @RequestParam(required = false) BigDecimal totalPriceMax,
            @RequestParam(required = false) BigDecimal totalPriceMin,
            @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {


        Specification<PurchaseModel> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and(PurchaseSpecification.hasCustomer(customerId));
        }

        if (info != null && !info.isEmpty()) {
            spec = spec.and(PurchaseSpecification.hasShopProductInfo(info));

        }
        if(totalPriceMax != null){
            spec =spec.and(PurchaseSpecification.hasPriceMax(totalPriceMax));
        }

        if(totalPriceMin != null){
            spec =spec.and(PurchaseSpecification.hasPriceMin(totalPriceMin));
        }

        Page<PurchaseModel> purchasePage = purchaseRepository.findAll(spec,pageable);

        List<PurchaseDTO> purchaseDTOList = purchasePage.getContent()
                .stream()
                .map(purchase -> {
                    PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(purchase);
                    // Convertir info (ShopProductInfoModel) a ShopProductInfoDTO
                    purchaseDTO.setProducts(purchase.getProducts().stream()
                            .map(shopProductInfoMapper::shopProductInfoModelToShopProductInfoDTO)
                            .collect(Collectors.toList()));
                    return purchaseDTO;
                })
                .collect(Collectors.toList());

        PageResponse<PurchaseDTO> pageResponse = new PageResponse<>(
                purchaseDTOList,
                purchasePage.getTotalElements(),
                purchasePage.getTotalPages()
        );
        return ResponseEntity.ok(pageResponse);

    }


    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDTO> getPurchaseById(@PathVariable Integer purchaseId){

        Optional<PurchaseModel> optionalPurchasePage = purchaseRepository.findById(purchaseId);

        PurchaseModel purchaseModel = optionalPurchasePage.get();

        return ResponseEntity.ok(purchaseMapper.purchaseModelToPurchaseDTO(purchaseModel));

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
    public ResponseEntity<PurchaseDTO> postPurchase(@Valid @RequestBody PostPurchaseDTO postPurchaseDTO) {
        CustomerModel customerModel = customerRepository.findById(postPurchaseDTO.getCustomerId()).orElse(null);

        if(customerModel == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        PurchaseModel purchaseModel =new PurchaseModel();
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(BigDecimal.ZERO);

        PurchaseModel savedPurchaseModel = purchaseRepository.save(purchaseModel);

        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(savedPurchaseModel);

        return ResponseEntity.ok(purchaseDTO);
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

    @PostMapping("/{purchaseId}/shopProductInfo/{shopProductInfoId}")
    public ResponseEntity<PurchaseDTO> addProductPurchase(@PathVariable Integer purchaseId, @PathVariable Integer shopProductInfoId) {


        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
        Optional<ShopProductInfoModel> optionalShopProductInfoModel = shopProductInfoRepository.findById(shopProductInfoId);

        if (optionalPurchaseModel.isEmpty() || optionalShopProductInfoModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        PurchaseModel purchaseModel = optionalPurchaseModel.get();
        ShopProductInfoModel shopProductInfoModel = optionalShopProductInfoModel.get();

        purchaseModel.getProducts().add(shopProductInfoModel);

        System.out.println(purchaseMapper.purchaseModelToPurchaseDTO(purchaseModel));

        PurchaseModel purchaseModelDB = purchaseRepository.save(purchaseModel);

        return ResponseEntity.ok(purchaseMapper.purchaseModelToPurchaseDTO(purchaseModelDB));

    }


    @DeleteMapping("{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer purchaseId) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchaseRepository.deleteById(purchaseId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{purchaseId}/shopProductInfo/{shopProductInfoId}")
    public ResponseEntity<Void> deleteProductPurchase(@PathVariable Integer purchaseId, @PathVariable Integer shopProductInfoId){
        if (purchaseRepository.existsById(purchaseId) && shopProductInfoRepository.existsById(shopProductInfoId)) {
            shopProductInfoRepository.deleteById(shopProductInfoId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

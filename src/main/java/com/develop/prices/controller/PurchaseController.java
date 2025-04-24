package com.develop.prices.controller;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.mapper.ProductPriceMapper;
import com.develop.prices.mapper.PurchaseMapper;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.PurchaseModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.PurchaseRepository;
import com.develop.prices.specification.PurchaseSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/purchases")
public class PurchaseController {
    private PurchaseRepository purchaseRepository;
    private CustomerRepository customerRepository;
    private ProductPriceRepository productPriceRepository;
    private PurchaseMapper purchaseMapper;
    private ProductPriceMapper productPriceMapper;

    public PurchaseController(CustomerRepository customerRepository, PurchaseRepository purchaseRepository, ProductPriceRepository productPriceRepository, PurchaseMapper purchaseMapper, ProductPriceMapper productPriceMapper) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.productPriceRepository = productPriceRepository;
        this.purchaseMapper = purchaseMapper;
        this.productPriceMapper = productPriceMapper;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<PurchaseDTO>> getPurchasesWithFilters(
            @RequestParam(required = false) Integer purchaseId,
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Set<ProductPriceModel> info,
            @RequestParam(required = false) BigDecimal totalPriceMax,
            @RequestParam(required = false) BigDecimal totalPriceMin,

            @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {


        Specification<PurchaseModel> spec = Specification.where(null);

        if (purchaseId != null) {
            spec = spec.and(PurchaseSpecification.hasPurchaseId(purchaseId));
        }

        if (customerId != null) {
            spec = spec.and(PurchaseSpecification.hasCustomer(customerId));
        }

        if (info != null && !info.isEmpty()) {
            spec = spec.and(PurchaseSpecification.hasProductPrice(info));

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
                    // Convertir info (ProductPriceModel) a ProductPriceDTO
                    purchaseDTO.setProducts(purchase.getProductPriceModel().stream()
                            .map(productPrice -> productPriceMapper.productPriceModelToProductPriceDTO(productPrice))
                            .collect(Collectors.toSet()));
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

    @PostMapping("")
    public ResponseEntity<PurchaseDTO> postPurchase(@Valid @RequestBody PostPurchaseDTO postPurchaseDTO) {
        CustomerModel customerModel = customerRepository.findById(postPurchaseDTO.getCustomerId()).orElse(null);

        PurchaseModel purchaseModel =new PurchaseModel();
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setProductPriceModel(Set.of());
        purchaseModel.setTotalPrice(BigDecimal.ZERO);

        PurchaseModel savedPurchaseModel = purchaseRepository.save(purchaseModel);


        PurchaseDTO purchaseDTO = purchaseMapper.purchaseModelToPurchaseDTO(savedPurchaseModel);

        return ResponseEntity.ok(purchaseDTO);
    }

    @DeleteMapping("{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer purchaseId) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchaseRepository.deleteById(purchaseId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}

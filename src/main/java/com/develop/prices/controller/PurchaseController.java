package com.develop.prices.controller;

import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.mapper.ProductPriceMapper;
import com.develop.prices.mapper.PurchaseMapper;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.PurchaseModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.ProductPriceRepository;
import com.develop.prices.repository.PurchaseRepository;
import com.develop.prices.specification.PurchaseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) CustomerDTO customer,
            @RequestParam(required = false) Set<ProductPriceModel> info,
            @RequestParam(required = false) BigDecimal totalPrice,
            @PageableDefault(sort = "purchaseId", direction = Sort.Direction.ASC) Pageable pageable) {


        Specification<PurchaseModel> spec = Specification.where(null);

        if (purchaseId != null) {
            spec = spec.and(PurchaseSpecification.hasPurchaseId(purchaseId));
        }

        if (customer != null) {
            spec = spec.and(PurchaseSpecification.hasCustomer(customer));
        }

        if (info != null && !info.isEmpty()) {
            spec = spec.and(PurchaseSpecification.hasProductPrice(info));

        }

        Page<PurchaseModel> purchasePage = purchaseRepository.findAll(spec,pageable);

        List<PurchaseDTO> purchaseDTOList = purchasePage.getContent()
                .stream()
                .map(purchase -> {
                    PurchaseDTO dto = purchaseMapper.purchaseModelToPurchaseDTO(purchase);
                    // Convertir info (ProductPriceModel) a ProductPriceDTO
                    dto.setInfo(purchase.getInfo().stream()
                            .map(productPrice -> productPriceMapper.productPriceModelToProductPriceDTO(productPrice))
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());

        PageResponse<PurchaseDTO> pageResponse = new PageResponse<>(
                purchaseDTOList,
                purchasePage.getTotalElements(),
                purchasePage.getTotalPages()
        );
        return ResponseEntity.ok(pageResponse);

    }
}

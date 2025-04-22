package com.develop.prices.controller;

import com.develop.prices.mapper.CustomerMapper;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.dto.CustomerDTO;
import com.develop.prices.dto.PageResponse;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.PurchaseRepository;
import com.develop.prices.specification.CustomerSpecification;
import com.develop.prices.specification.ProductPriceSpecification;
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

import java.util.List;

@RestController
@Transactional
@RequestMapping("/customers")
public class CustomerController {
    private CustomerRepository customerRepository;
    private PurchaseRepository purchaseRepository;
    private CustomerMapper customerMapper;

    public CustomerController(CustomerRepository customerRepository, PurchaseRepository purchaseRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.customerMapper = customerMapper;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<CustomerDTO>> getCustomersWithFilters(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer phone,
            @RequestParam(required = false) String email,
        @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {


            Specification<CustomerModel> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and(CustomerSpecification.hasCustomerId(customerId));
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and(CustomerSpecification.hasName(name));
        }

        if (phone != null) {
            spec = spec.and(CustomerSpecification.hasPhone(phone));
        }

        if (email != null && !email.isBlank()) {
            spec = spec.and(CustomerSpecification.hasEmail(email));
        }



            Page<CustomerModel> customerPage = customerRepository.findAll(spec,pageable);
            List<CustomerDTO> customerDTOList = customerPage.getContent()
                    .stream()
                    .map(customerMapper::customerModelToCustomerDTO)
                    .toList();

            PageResponse<CustomerDTO> pageResponse = new PageResponse<>(
                    customerDTOList,
                    customerPage.getTotalElements(),
                    customerPage.getTotalPages()
            );
            return ResponseEntity.ok(pageResponse);
        }



}

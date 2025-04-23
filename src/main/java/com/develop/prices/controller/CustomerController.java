package com.develop.prices.controller;

import com.develop.prices.dto.*;
import com.develop.prices.mapper.CustomerMapper;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.PurchaseRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PageResponse> getAllCustomers(
        @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {

            Page<CustomerModel> customerPage = customerRepository.findAll(pageable);
            List<CustomerDTO> customerDTOList = customerPage.getContent()
                    .stream()
                    .map(customerMapper::customerModelToCustomerDTO)
                    .toList();

            PageResponse pageResponse = new PageResponse(
                    customerDTOList,
                    customerPage.getTotalElements(),
                    customerPage.getTotalPages()
            );
            return ResponseEntity.ok(pageResponse);

        }

    @PostMapping("")
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {

        CustomerModel newCustomerModel = new CustomerModel();

        newCustomerModel.setName(createCustomerDTO.getName());
        newCustomerModel.setPhone(createCustomerDTO.getPhone());
        newCustomerModel.setEmail(createCustomerDTO.getEmail());

        CustomerModel customerModel = customerRepository.save(newCustomerModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.customerModelToCustomerDTO(customerModel));
    }



}

package com.develop.prices.controller;

import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.dto.CustomerDTO;
import com.develop.prices.dto.PageResponse;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/customers")
public class CustomerController {
    private CustomerRepository customerRepository;
    private PurchaseRepository purchaseRepository;

    public CustomerController(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse> getAllCustomers(
        @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {

            Page<CustomerModel> customerPage = customerRepository.findAll(pageable);
            List<CustomerDTO> customerDTOList = customerPage.getContent().stream().map(this::toCustomerDTO).toList();

            PageResponse pageResponse = new PageResponse(
                    customerDTOList,
                    customerPage.getTotalElements(),
                    customerPage.getTotalPages()
            );
            return ResponseEntity.ok(pageResponse);

        }


    private CustomerDTO toCustomerDTO (CustomerModel customerModel){
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerId(customerModel.getCustomerId());
        customerDTO.setName(customerModel.getName());
        customerDTO.setEmail(customerModel.getEmail());
        customerDTO.setPhone(customerModel.getPhone());

        return customerDTO;
    }
}

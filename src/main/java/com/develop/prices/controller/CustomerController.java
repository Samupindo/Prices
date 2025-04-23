package com.develop.prices.controller;

import com.develop.prices.dto.*;
import com.develop.prices.mapper.CustomerMapper;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ShopModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.PurchaseRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.develop.prices.specification.CustomerSpecification;
import com.develop.prices.specification.ProductPriceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {

        CustomerModel newCustomerModel = new CustomerModel();

        newCustomerModel.setName(createCustomerDTO.getName());
        newCustomerModel.setPhone(createCustomerDTO.getPhone());
        newCustomerModel.setEmail(createCustomerDTO.getEmail());

        CustomerModel customerModel = customerRepository.save(newCustomerModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.customerModelToCustomerDTO(customerModel));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateProduct(@PathVariable Integer customerId, @Valid @RequestBody CustomerPutDTO customerPutDTO){
      CustomerModel customerModel = customerRepository.findById(customerId).orElse(null);

      if(customerModel == null){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      customerModel.setName(customerPutDTO.getName());
      customerModel.setPhone(customerPutDTO.getPhone());
      customerModel.setEmail(customerPutDTO.getEmail());
      return ResponseEntity.ok(customerMapper.customerModelToCustomerDTO(customerModel));
    }



}

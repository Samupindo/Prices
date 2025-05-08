package com.develop.prices.rest;

import com.develop.prices.dto.*;
import com.develop.prices.mapper.CustomerModelMapper;
import com.develop.prices.entity.CustomerModel;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.specification.CustomerSpecification;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
//    private final CustomerRepository customerRepository;
//    private final CustomerModelMapper customerModelMapper;
//
//    public CustomerController(CustomerRepository customerRepository, CustomerModelMapper customerModelMapper) {
//        this.customerRepository = customerRepository;
//        this.customerModelMapper = customerModelMapper;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<PageResponse<CustomerDTO>> getCustomersWithFilters(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Integer phone,
//            @RequestParam(required = false) String email,
//            @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {
//
//
//        Specification<CustomerModel> spec = Specification.where(null);
//
//
//        if (name != null && !name.isBlank()) {
//            spec = spec.and(CustomerSpecification.hasName(name));
//        }
//
//        if (phone != null) {
//            spec = spec.and(CustomerSpecification.hasPhone(phone));
//        }
//
//        if (email != null && !email.isBlank()) {
//            spec = spec.and(CustomerSpecification.hasEmail(email));
//        }
//
//
//        Page<CustomerModel> customerPage = customerRepository.findAll(spec, pageable);
//        List<CustomerDTO> customerDTOList = customerPage.getContent()
//                .stream()
//                .map(customerModelMapper::toCustomerTo)
//                .toList();
//
//        PageResponse<CustomerDTO> pageResponse = new PageResponse<>(
//                customerDTOList,
//                customerPage.getTotalElements(),
//                customerPage.getTotalPages()
//        );
//        return ResponseEntity.ok(pageResponse);
//
//    }
//
//    @GetMapping("/{customerId}")
//    public ResponseEntity<CustomerDTO> getProductById(@PathVariable Integer customerId) {
//        Optional<CustomerModel> optionalCustomerModel = customerRepository.findById(customerId);
//        if (optionalCustomerModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        CustomerModel customerModel = optionalCustomerModel.get();
//
//        return ResponseEntity.ok(customerModelMapper.toCustomerTo(customerModel));
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
//    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
//
//        CustomerModel newCustomerModel = new CustomerModel();
//
//        newCustomerModel.setName(createCustomerDTO.getName());
//        newCustomerModel.setPhone(createCustomerDTO.getPhone());
//        newCustomerModel.setEmail(createCustomerDTO.getEmail());
//
//        CustomerModel customerModel = customerRepository.save(newCustomerModel);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(customerModelMapper.toCustomerTo(customerModel));
//    }
//
//    @PutMapping("/{customerId}")
//    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CustomerPutDTO customerPutDTO) {
//        CustomerModel customerModel = customerRepository.findById(customerId).orElse(null);
//
//        if (customerModel == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//
//        customerModel.setName(customerPutDTO.getName());
//        customerModel.setPhone(customerPutDTO.getPhone());
//        customerModel.setEmail(customerPutDTO.getEmail());
//
//
//        return ResponseEntity.ok(customerModelMapper.toCustomerTo(customerModel));
//
//    }
//
//    @PatchMapping("/{customerId}")
//    public ResponseEntity<CustomerDTO> partialUpdateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
//
//        Optional<CustomerModel> optionalCustomerModel = customerRepository.findById(customerId);
//        if (optionalCustomerModel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        CustomerModel customerModel = optionalCustomerModel.get();
//
//
//        if (createCustomerDTO.getName() != null) {
//            if (createCustomerDTO.getName().trim().isEmpty()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//            }
//            customerModel.setName(createCustomerDTO.getName());
//        }
//
//        if (createCustomerDTO.getPhone() != null) {
//            customerModel.setPhone(createCustomerDTO.getPhone());
//        }
//
//        if (createCustomerDTO.getEmail() != null) {
//            if (createCustomerDTO.getEmail().trim().isEmpty()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//            }
//            customerModel.setEmail(createCustomerDTO.getEmail());
//        }
//
//
//        return ResponseEntity.ok(customerModelMapper.toCustomerTo(customerModel));
//    }
//
//
//
//
//    @DeleteMapping("/{customerId}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
//        if (customerRepository.existsById(customerId)) {
//            customerRepository.deleteById(customerId);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
}

package com.develop.prices.rest;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.mapper.CustomerRestMapper;
import com.develop.prices.service.CustomerService;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRestMapper customerRestMapper;

    public CustomerController(CustomerService customerService, CustomerRestMapper customerRestMapper) {
        this.customerService = customerService;
        this.customerRestMapper = customerRestMapper;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<CustomerDTO>> getCustomersWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer phone,
            @RequestParam(required = false) String email,
            @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {


        PageResponse<CustomerTo> customerPage = customerService.findAllWithFilters(name, phone, email, pageable);

        List<CustomerDTO> customerDTOList = customerPage.getContent()
                .stream()
                .map(customerRestMapper::toCustomerDTO)
                .toList();

        PageResponse<CustomerDTO> pageResponse = new PageResponse<>(
                customerDTOList,
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
        return ResponseEntity.ok(pageResponse);

    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getProductById(@PathVariable Integer customerId) {

        Optional<CustomerTo> optionalCustomerTo = customerService.findByCustomerId(customerId);

        CustomerTo customerTo = optionalCustomerTo.get();

        return ResponseEntity.ok(customerRestMapper.toCustomerDTO(customerTo));
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

        CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDTO);

        CustomerTo newCustomerModel = customerService.saveCustomer(createCustomerTo);


        return ResponseEntity.status(HttpStatus.CREATED).body(customerRestMapper.toCustomerDTO(newCustomerModel));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CustomerPutDTO customerPutDTO) {

        CustomerPutTo customerTo = customerRestMapper.toCustomerPutTo(customerPutDTO);

        CustomerDTO customerDTO = customerRestMapper.toCustomerDTO(customerService.updateCustomer(customerId, customerTo));

        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);

    }
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

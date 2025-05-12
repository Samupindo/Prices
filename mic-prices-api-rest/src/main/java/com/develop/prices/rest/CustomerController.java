package com.develop.prices.rest;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.dto.PageResponseDTO;
import com.develop.prices.mapper.CustomerRestMapper;
import com.develop.prices.service.CustomerService;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<PageResponseDTO<CustomerDTO>> getCustomersWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer phone,
            @RequestParam(required = false) String email,
            @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {


        PageResponseTo<CustomerTo> customerPage = customerService.findAllWithFilters(name, phone, email, pageable);

        List<CustomerDTO> customerDTOList = customerPage.getContent()
                .stream()
                .map(customerRestMapper::toCustomerDTO)
                .toList();

        PageResponseDTO<CustomerDTO> pageResponseDTO = new PageResponseDTO<>(
                customerDTOList,
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
        return ResponseEntity.ok(pageResponseDTO);

    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getProductById(@PathVariable Integer customerId) {

        CustomerTo customerTo = customerService.findByCustomerId(customerId);


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

        return ResponseEntity.ok().body(customerDTO);

    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> partialUpdateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CreateCustomerDTO createCustomerDTO) {

        CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDTO);

        CustomerDTO customerDTO = customerRestMapper.toCustomerDTO(customerService.updatePatchCustomer(customerId, createCustomerTo));

        return ResponseEntity.ok().body(customerDTO);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok().build();
    }

}

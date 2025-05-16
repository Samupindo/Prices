package com.develop.prices.rest;

import com.develop.prices.dto.CreateCustomerDto;
import com.develop.prices.dto.CustomerDto;
import com.develop.prices.dto.CustomerPutDto;
import com.develop.prices.dto.PageResponseDto;
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
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerService customerService;
  private final CustomerRestMapper customerRestMapper;

  public CustomerController(
      CustomerService customerService, CustomerRestMapper customerRestMapper) {
    this.customerService = customerService;
    this.customerRestMapper = customerRestMapper;
  }

  @GetMapping("")
  public ResponseEntity<PageResponseDto<CustomerDto>> getCustomersWithFilters(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer phone,
      @RequestParam(required = false) String email,
      @PageableDefault(sort = "customerId", direction = Sort.Direction.ASC) Pageable pageable) {

    PageResponseTo<CustomerTo> customerPage =
        customerService.findAllWithFilters(name, phone, email, pageable);

    List<CustomerDto> customerDtoList =
        customerPage.getContent().stream().map(customerRestMapper::toCustomerDto).toList();

    PageResponseDto<CustomerDto> pageResponseDto =
        new PageResponseDto<>(
            customerDtoList, customerPage.getTotalElements(), customerPage.getTotalPages());
    return ResponseEntity.ok(pageResponseDto);
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer customerId) {

    CustomerTo customerTo = customerService.findByCustomerId(customerId);

    return ResponseEntity.ok(customerRestMapper.toCustomerDto(customerTo));
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(value = "{ \"error\": \"Missing required field: name\" }")))
      })
  @PostMapping("")
  public ResponseEntity<CustomerDto> addCustomer(
      @Valid @RequestBody CreateCustomerDto createCustomerDto) {

    CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDto);

    CustomerTo newCustomerModel = customerService.saveCustomer(createCustomerTo);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerRestMapper.toCustomerDto(newCustomerModel));
  }

  @PutMapping("/{customerId}")
  public ResponseEntity<CustomerDto> updateCustomer(
      @PathVariable Integer customerId, @Valid @RequestBody CustomerPutDto customerPutDto) {

    CustomerPutTo customerTo = customerRestMapper.toCustomerPutTo(customerPutDto);

    CustomerDto customerDto =
        customerRestMapper.toCustomerDto(customerService.updateCustomer(customerId, customerTo));

    return ResponseEntity.ok().body(customerDto);
  }

  @PatchMapping("/{customerId}")
  public ResponseEntity<CustomerDto> partialUpdateCustomer(
      @PathVariable Integer customerId, @Valid @RequestBody CreateCustomerDto createCustomerDto) {

    CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDto);

    CustomerDto customerDto =
        customerRestMapper.toCustomerDto(
            customerService.updatePatchCustomer(customerId, createCustomerTo));

    return ResponseEntity.ok().body(customerDto);
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
    customerService.deleteCustomer(customerId);

    return ResponseEntity.ok().build();
  }
}

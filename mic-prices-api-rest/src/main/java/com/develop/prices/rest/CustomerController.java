package com.develop.prices.rest;

import com.develop.prices.controller.CustomersApi;
import com.develop.prices.dto.CreateCustomerDto;
import com.develop.prices.dto.CustomerDto;
import com.develop.prices.dto.CustomerPutDto;
import com.develop.prices.dto.PageResponseDtoCustomerDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CustomerController implements CustomersApi {
  private final CustomerService customerService;
  private final CustomerRestMapper customerRestMapper;

  public CustomerController(
      CustomerService customerService, CustomerRestMapper customerRestMapper) {
    this.customerService = customerService;
    this.customerRestMapper = customerRestMapper;
  }

  @Override
  public ResponseEntity<PageResponseDtoCustomerDto> getCustomersWithFilters(String name,
                                                                            Integer phone,
                                                                            String email,
                                                                            Integer pageable,
                                                                            Integer size,
                                                                            String sort) {
    PageResponseTo<CustomerTo> customerPage =
        customerService.findAllWithFilters(name, phone, email,
            Pageable.unpaged(Sort.by(Sort.Direction.DESC)));

    List<CustomerDto> customerDtoList =
        customerPage.getContent().stream().map(customerRestMapper::toCustomerDto).toList();

    PageResponseDtoCustomerDto response = new PageResponseDtoCustomerDto();
    response.setContent(customerDtoList);
    response.setTotalElements(customerPage.getTotalElements());
    response.setTotalPages(customerPage.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CustomerDto> getProductById(Integer customerId) {
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
                  @ExampleObject(value = "{ \"error\": "
                      +
                      "\"Missing required field: name\" }")))
      })


  @Override
  public ResponseEntity<CustomerDto> addCustomer(CreateCustomerDto createCustomerDto) {
    CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDto);

    CustomerTo newCustomerModel = customerService.saveCustomer(createCustomerTo);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerRestMapper.toCustomerDto(newCustomerModel));
  }

  @Override
  public ResponseEntity<CustomerDto> updateCustomer(Integer customerId,
                                                    CustomerPutDto customerPutDto) {
    CustomerPutTo customerTo = customerRestMapper.toCustomerPutTo(customerPutDto);

    CustomerDto customerDto =
        customerRestMapper.toCustomerDto(customerService.updateCustomer(customerId, customerTo));

    return ResponseEntity.ok().body(customerDto);
  }

  @Override
  public ResponseEntity<CustomerDto> partialUpdateCustomer(Integer customerId,
                                                           CreateCustomerDto createCustomerDto) {
    CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDto);

    CustomerDto customerDto =
        customerRestMapper.toCustomerDto(
            customerService.updatePatchCustomer(customerId, createCustomerTo));

    return ResponseEntity.ok().body(customerDto);
  }


  @Override
  public ResponseEntity<Void> deleteCustomer(Integer customerId) {
    customerService.deleteCustomer(customerId);

    return ResponseEntity.ok().build();
  }
}

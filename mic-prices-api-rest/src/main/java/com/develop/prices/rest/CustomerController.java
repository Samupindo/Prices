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
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<PageResponseDtoCustomerDto> getCustomersWithFilters(
      String name, Integer phone, String email, Pageable pageable) {

    pageable =
        PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "customerId"));
    PageResponseTo<CustomerTo> customerPage =
        customerService.findAllWithFilters(name, phone, email, pageable);

    List<CustomerDto> customerDtoList =
        customerPage.getContent().stream().map(customerRestMapper::toCustomerDto).toList();

    PageResponseDtoCustomerDto response = new PageResponseDtoCustomerDto();
    response.setContent(customerDtoList);
    response.setTotalElements(customerPage.getTotalElements());
    response.setTotalPages(customerPage.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CustomerDto> getCustomerById(Integer customerId) {
    CustomerTo customerTo = customerService.findByCustomerId(customerId);

    return ResponseEntity.ok(customerRestMapper.toCustomerDto(customerTo));
  }

  @Override
  public ResponseEntity<CustomerDto> addCustomer(CreateCustomerDto createCustomerDto) {
    CreateCustomerTo createCustomerTo = customerRestMapper.toCreateCustomerTo(createCustomerDto);

    CustomerTo newCustomerModel = customerService.saveCustomer(createCustomerTo);

    CustomerDto customerDto = customerRestMapper.toCustomerDto(newCustomerModel);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerDto);
  }

  @Override
  public ResponseEntity<CustomerDto> updateCustomer(
      Integer customerId, CustomerPutDto customerPutDto) {
    CustomerPutTo customerTo = customerRestMapper.toCustomerPutTo(customerPutDto);

    CustomerDto customerDto =
        customerRestMapper.toCustomerDto(customerService.updateCustomer(customerId, customerTo));

    return ResponseEntity.ok().body(customerDto);
  }

  @Override
  public ResponseEntity<CustomerDto> partialUpdateCustomer(
      Integer customerId, CreateCustomerDto createCustomerDto) {
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

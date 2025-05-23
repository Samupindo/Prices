package com.develop.prices.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.develop.prices.dto.CreateCustomerDto;
import com.develop.prices.dto.CustomerDto;
import com.develop.prices.dto.CustomerPutDto;
import com.develop.prices.dto.PageResponseDto;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.CustomerRestMapper;
import com.develop.prices.service.CustomerService;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomerControllerTest {

  private final CustomerRestMapper customerRestMapper = Mappers.getMapper(CustomerRestMapper.class);
  private CustomerController customerController;
  private CustomerService customerService;

  @BeforeEach
  void init() {
    customerService = mock(CustomerService.class);
    customerController = new CustomerController(customerService, customerRestMapper);
  }

  @Test
  void testGetCustomersWithFilters() {
    String name = "Brais";
    Integer phone = 999999999;
    String email = "brais@example.com";
    Pageable pageable = PageRequest.of(0, 10, Sort.by("customerId").ascending());

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Brais");
    customerTo.setEmail("brais@example.com");
    customerTo.setPhone(999999999);

    List<CustomerTo> customerToList = List.of(customerTo);
    PageResponseTo<CustomerTo> customerToPageResponseTo =
        new PageResponseTo<>(customerToList, 1, 1);

    when(customerService.findAllWithFilters(eq(name), eq(phone), eq(email), any(Pageable.class)))
        .thenReturn(customerToPageResponseTo);

    ResponseEntity<PageResponseDto<CustomerDto>> response =
        customerController.getCustomersWithFilters(name, phone, email, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertNotNull(response.getBody());

    PageResponseDto<CustomerDto> body = response.getBody();
    assertEquals(1, body.getTotalElements());
    assertEquals(1, body.getTotalPages());
    assertEquals(1, body.getContent().size());

    CustomerDto resultDTO = body.getContent().getFirst();
    assertEquals("Brais", resultDTO.getName());
    assertEquals("brais@example.com", resultDTO.getEmail());
    assertEquals(999999999, resultDTO.getPhone());
  }

  @Test
  void testGetCustomersWithFiltersOnlyName() {
    String name = "Brais";
    Integer phone = null;
    String email = null;
    Pageable pageable = PageRequest.of(0, 10, Sort.by("customerId").ascending());

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Brais");
    customerTo.setEmail("brais@example.com");
    customerTo.setPhone(999999999);

    List<CustomerTo> customerToList = List.of(customerTo);
    PageResponseTo<CustomerTo> customerToPageResponseTo =
        new PageResponseTo<>(customerToList, 1, 1);

    when(customerService.findAllWithFilters(eq(name), eq(phone), eq(email), any(Pageable.class)))
        .thenReturn(customerToPageResponseTo);

    ResponseEntity<PageResponseDto<CustomerDto>> response =
        customerController.getCustomersWithFilters(name, phone, email, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertNotNull(response.getBody());

    PageResponseDto<CustomerDto> body = response.getBody();
    assertEquals(1, body.getTotalElements());
    assertEquals(1, body.getTotalPages());
    assertEquals(1, body.getContent().size());

    CustomerDto resultDTO = body.getContent().getFirst();
    assertEquals("Brais", resultDTO.getName());
    assertEquals("brais@example.com", resultDTO.getEmail());
    assertEquals(999999999, resultDTO.getPhone());
  }

  @Test
  void testGetCustomersWithFiltersOnlyEmail() {
    String name = null;
    Integer phone = null;
    String email = "brais@example.com";
    Pageable pageable = PageRequest.of(0, 10, Sort.by("customerId").ascending());

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Brais");
    customerTo.setEmail("brais@example.com");
    customerTo.setPhone(999999999);

    List<CustomerTo> customerToList = List.of(customerTo);
    PageResponseTo<CustomerTo> customerToPageResponseTo =
        new PageResponseTo<>(customerToList, 1, 1);

    when(customerService.findAllWithFilters(eq(name), eq(phone), eq(email), any(Pageable.class)))
        .thenReturn(customerToPageResponseTo);

    ResponseEntity<PageResponseDto<CustomerDto>> response =
        customerController.getCustomersWithFilters(name, phone, email, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertNotNull(response.getBody());

    PageResponseDto<CustomerDto> body = response.getBody();
    assertEquals(1, body.getTotalElements());
    assertEquals(1, body.getTotalPages());
    assertEquals(1, body.getContent().size());

    CustomerDto resultDTO = body.getContent().getFirst();
    assertEquals("Brais", resultDTO.getName());
    assertEquals("brais@example.com", resultDTO.getEmail());
    assertEquals(999999999, resultDTO.getPhone());
  }

  @Test
  void testGetCustomersWithFiltersOnlyPhone() {
    String name = null;
    Integer phone = 999999999;
    String email = null;
    Pageable pageable = PageRequest.of(0, 10, Sort.by("customerId").ascending());

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Brais");
    customerTo.setEmail("brais@example.com");
    customerTo.setPhone(999999999);

    List<CustomerTo> customerToList = List.of(customerTo);
    PageResponseTo<CustomerTo> customerToPageResponseTo =
        new PageResponseTo<>(customerToList, 1, 1);

    when(customerService.findAllWithFilters(eq(name), eq(phone), eq(email), any(Pageable.class)))
        .thenReturn(customerToPageResponseTo);

    ResponseEntity<PageResponseDto<CustomerDto>> response =
        customerController.getCustomersWithFilters(name, phone, email, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertNotNull(response.getBody());

    PageResponseDto<CustomerDto> body = response.getBody();
    assertEquals(1, body.getTotalElements());
    assertEquals(1, body.getTotalPages());
    assertEquals(1, body.getContent().size());

    CustomerDto resultDTO = body.getContent().getFirst();
    assertEquals("Brais", resultDTO.getName());
    assertEquals("brais@example.com", resultDTO.getEmail());
    assertEquals(999999999, resultDTO.getPhone());
  }

  @Test
  void testGetCustomersWithoutFilters() {
    String name = null;
    Integer phone = null;
    String email = null;
    Pageable pageable = PageRequest.of(0, 10, Sort.by("customerId").ascending());

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Brais");
    customerTo.setEmail("brais@example.com");
    customerTo.setPhone(999999999);

    List<CustomerTo> customerToList = List.of(customerTo);
    PageResponseTo<CustomerTo> customerToPageResponseTo =
        new PageResponseTo<>(customerToList, 1, 1);

    when(customerService.findAllWithFilters(eq(name), eq(phone), eq(email), any(Pageable.class)))
        .thenReturn(customerToPageResponseTo);

    ResponseEntity<PageResponseDto<CustomerDto>> response =
        customerController.getCustomersWithFilters(name, phone, email, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertNotNull(response.getBody());

    PageResponseDto<CustomerDto> body = response.getBody();
    assertEquals(1, body.getTotalElements());
    assertEquals(1, body.getTotalPages());
    assertEquals(1, body.getContent().size());

    CustomerDto resultDTO = body.getContent().getFirst();
    assertEquals("Brais", resultDTO.getName());
    assertEquals("brais@example.com", resultDTO.getEmail());
    assertEquals(999999999, resultDTO.getPhone());
  }

  @Test
  void testGetCustomerById() {
    Integer customerId = 1;

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(customerId);

    when(customerService.findByCustomerId(customerId)).thenReturn(customerTo);

    ResponseEntity<CustomerDto> response =
        customerController.getCustomerById(customerTo.getCustomerId());

    assertEquals(HttpStatus.OK, response.getStatusCode());

    CustomerDto customerDTO = response.getBody();

    assertNotNull(customerDTO);

    assertEquals(customerTo.getCustomerId(), customerDTO.getCustomerId());
  }

  @Test
  void testGetCustomerByIdNotFound() {
    Integer customerId = 1;

    when(customerService.findByCustomerId(customerId)).thenThrow(new InstanceNotFoundException());

    assertThrows(
        InstanceNotFoundException.class,
        () -> {
          customerController.getCustomerById(customerId);
        });

    verify(customerService, times(1)).findByCustomerId(customerId);
  }

  @Test
  void testAddCustomer() {

    CreateCustomerDto createCustomerDTO = new CreateCustomerDto();
    createCustomerDTO.setName("Brais");
    createCustomerDTO.setEmail("brais@example.com");
    createCustomerDTO.setPhone(999999999);

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(1);
    customerTo.setName("Luis");
    customerTo.setEmail("luis@example.com");
    customerTo.setPhone(888888888);

    when(customerService.saveCustomer(any(CreateCustomerTo.class))).thenReturn(customerTo);

    ResponseEntity<CustomerDto> response = customerController.addCustomer(createCustomerDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    CustomerDto customerDTO = response.getBody();
    assertNotNull(customerDTO);

    assertEquals(customerTo.getCustomerId(), customerDTO.getCustomerId());
    assertEquals(customerTo.getName(), customerDTO.getName());
    assertEquals(customerTo.getEmail(), customerDTO.getEmail());
    assertEquals(customerTo.getPhone(), customerDTO.getPhone());
  }

  @Test
  void testAddCustomerFailure() {
    CreateCustomerDto createCustomerDTO = new CreateCustomerDto();
    createCustomerDTO.setName("Brais");
    createCustomerDTO.setEmail("brais@gmail.com");
    createCustomerDTO.setPhone(999999999);

    when(customerService.saveCustomer(any(CreateCustomerTo.class)))
        .thenThrow(new InstanceNotFoundException());

    assertThrows(
        InstanceNotFoundException.class, () -> customerController.addCustomer(createCustomerDTO));
  }

  @Test
  void testUpdateCustomer() {
    Integer customerId = 1;

    CustomerPutDto customerPutTo = new CustomerPutDto();
    customerPutTo.setName("Brais");
    customerPutTo.setEmail("brais@gmail.com");
    customerPutTo.setPhone(999999999);

    CustomerTo customerTo = new CustomerTo();
    customerTo.setName("Luis");
    customerTo.setEmail("luis@example.com");
    customerTo.setPhone(888888888);

    when(customerService.updateCustomer(eq(customerId), any(CustomerPutTo.class)))
        .thenReturn(customerTo);

    ResponseEntity<CustomerDto> response =
        customerController.updateCustomer(customerId, customerPutTo);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    CustomerDto customerDTO = response.getBody();
    assertNotNull(customerDTO);

    assertEquals(customerTo.getCustomerId(), customerDTO.getCustomerId());
    assertEquals("Luis", customerDTO.getName());
    assertEquals("luis@example.com", customerDTO.getEmail());
    assertEquals(888888888, customerDTO.getPhone());
  }

  @Test
  void testUpdateCustomerNotFound() {
    Integer customerId = 1;

    CustomerPutDto customerPutDTO = new CustomerPutDto();
    customerPutDTO.setName("Brais");
    customerPutDTO.setEmail("brais@example.com");
    customerPutDTO.setPhone(999999999);

    when(customerService.updateCustomer(eq(customerId), any(CustomerPutTo.class)))
        .thenThrow(new InstanceNotFoundException());

    assertThrows(
        InstanceNotFoundException.class,
        () -> customerController.updateCustomer(customerId, customerPutDTO));
  }

  @Test
  void testPartialUpdateCustomer() {
    Integer customerId = 1;

    CreateCustomerDto createCustomerDTO = new CreateCustomerDto();
    createCustomerDTO.setName("Pablo");

    CustomerTo customerTo = new CustomerTo();
    customerTo.setCustomerId(customerId);
    customerTo.setName("Pablo");
    customerTo.setEmail("pablo@example.com");
    customerTo.setPhone(111111111);

    when(customerService.updatePatchCustomer(eq(customerId), any(CreateCustomerTo.class)))
        .thenReturn(customerTo);

    ResponseEntity<CustomerDto> response =
        customerController.partialUpdateCustomer(customerId, createCustomerDTO);

    CustomerDto customerDTO = response.getBody();
    assertNotNull(customerDTO);

    assertEquals(customerId, customerDTO.getCustomerId());
    assertEquals("Pablo", customerDTO.getName());
    assertEquals("pablo@example.com", customerDTO.getEmail());
    assertEquals(111111111, customerDTO.getPhone());
  }

  @Test
  void testPartialUpdateCustomerNotFound() {
    Integer customerId = 1;

    CreateCustomerDto createCustomerDTO = new CreateCustomerDto();
    createCustomerDTO.setName("Brais");

    when(customerService.updatePatchCustomer(eq(customerId), any(CreateCustomerTo.class)))
        .thenThrow(new InstanceNotFoundException());

    assertThrows(
        InstanceNotFoundException.class,
        () -> customerController.partialUpdateCustomer(customerId, createCustomerDTO));
  }

  @Test
  void testDeleteCustomer() {
    Integer customerId = 1;

    doNothing().when(customerService).deleteCustomer(customerId);

    ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testDeleteCustomerNotFound() {
    Integer customerId = 1;

    // Para que el service simule que lanza la excepción
    doThrow(new InstanceNotFoundException()).when(customerService).deleteCustomer(customerId);

    assertThrows(
        InstanceNotFoundException.class, () -> customerController.deleteCustomer(customerId));
  }
}

package com.develop.prices.service;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.dto.PageResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllCustomers();

    PageResponse<CustomerDTO> findAllWithFilters(String name, Integer phone, String email);

    CustomerDTO findByCustomerId(Integer customerId);

    CustomerDTO saveCustomer(CreateCustomerDTO createCustomerDTO);

    CustomerDTO updateCustomer(Integer customerId, CustomerPutDTO customerPutDTO);

    void deleteCustomer(Integer customerId);


}

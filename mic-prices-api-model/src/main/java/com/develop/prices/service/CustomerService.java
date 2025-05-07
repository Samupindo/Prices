package com.develop.prices.service;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.dto.PageResponse;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;

import java.util.List;

public interface CustomerService {

    List<CustomerTo> findAllCustomers();

    List<CustomerTo> findAllWithFilters(String name, Integer phone, String email);

    CustomerTo findByCustomerId(Integer customerId);

    CustomerTo saveCustomer(CreateCustomerTo createCustomerTo);

    CustomerTo updateCustomer(Integer customerId, CustomerPutTo customerPutTo);

    CustomerTo updatePatchCustomer(Integer customerId, CreateCustomerTo createCustomerTo);

    void deleteCustomer(Integer customerId);


}

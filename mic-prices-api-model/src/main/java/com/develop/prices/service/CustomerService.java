package com.develop.prices.service;

import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    List<CustomerTo> findAllCustomers();

    List<CustomerTo> findAllWithFilters(String name, Integer phone, String email);

    PageResponse<CustomerTo> findAllWithFilters(String name, Integer phone, String email, Pageable pageable);

    CustomerTo findByCustomerId(Integer customerId);

    CustomerTo saveCustomer(CreateCustomerTo createCustomerTo);

    CustomerTo updateCustomer(Integer customerId, CustomerPutTo customerPutTo);

    CustomerTo updatePatchCustomer(Integer customerId, CreateCustomerTo createCustomerTo);

    void deleteCustomer(Integer customerId);


}

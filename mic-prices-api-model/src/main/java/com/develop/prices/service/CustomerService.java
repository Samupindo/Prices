package com.develop.prices.service;

import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  PageResponseTo<CustomerTo> findAllWithFilters(String name, Integer phone, String email, Pageable pageable);

  CustomerTo findByCustomerId(Integer customerId);

  CustomerTo saveCustomer(CreateCustomerTo createCustomerTo);

  CustomerTo updateCustomer(Integer customerId, CustomerPutTo customerPutTo);

  CustomerTo updatePatchCustomer(Integer customerId, CreateCustomerTo createCustomerTo);

  void deleteCustomer(Integer customerId);


}

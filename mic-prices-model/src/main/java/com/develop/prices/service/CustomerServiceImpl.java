package com.develop.prices.service;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.CustomerModelMapper;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.specification.CustomerSpecification;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerModelMapper customerModelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerModelMapper customerModelMapper) {
        this.customerRepository = customerRepository;
        this.customerModelMapper = customerModelMapper;
    }


    @Override
    public PageResponseTo<CustomerTo> findAllWithFilters(String name, Integer phone, String email, Pageable pageable) {


        Specification<CustomerModel> spec = Specification.where(null);


        if (name != null && !name.isBlank()) {
            spec = spec.and(CustomerSpecification.hasName(name));
        }

        if (phone != null) {
            spec = spec.and(CustomerSpecification.hasPhone(phone));
        }

        if (email != null && !email.isBlank()) {
            spec = spec.and(CustomerSpecification.hasEmail(email));
        }

        Page<CustomerModel> customerModels = customerRepository.findAll(spec,pageable);
        List<CustomerTo> customerTos = customerModels.getContent().stream().map(customerModelMapper::toCustomerTo).toList();
        
        
        PageResponseTo<CustomerTo> pageResponseTo = new PageResponseTo<>(
                customerTos,
                customerModels.getTotalElements(),
                customerModels.getTotalPages()
        );
        return customerModelMapper.toCustomerTo(pageResponseTo);

        
    }


    @Override
    public CustomerTo findByCustomerId(Integer customerId) {
        Optional<CustomerModel> optionalCustomerModel = customerRepository.findById(customerId);
        if (optionalCustomerModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        CustomerModel customerModel = optionalCustomerModel.get();

        return customerModelMapper.toCustomerTo(customerModel);
    }

    @Override
    public CustomerTo saveCustomer(CreateCustomerTo createCustomerTo) {
        CustomerModel customerModel = customerModelMapper.toCustomerModel(createCustomerTo);
        customerModel.setName(createCustomerTo.getName());
        customerModel.setPhone(createCustomerTo.getPhone());
        customerModel.setEmail(createCustomerTo.getEmail());

        CustomerModel savedCustomerModel = customerRepository.save(customerModel);

        return customerModelMapper.toCustomerTo(savedCustomerModel);
    }

    @Override
    public CustomerTo updateCustomer(Integer customerId, CustomerPutTo customerPutTo) {
        CustomerModel customerModel = customerRepository.findById(customerId).orElse(null);
        if (customerModel == null) {
            throw new InstanceNotFoundException();
        }

        customerModel.setName(customerPutTo.getName());
        customerModel.setPhone(customerPutTo.getPhone());
        customerModel.setEmail(customerPutTo.getEmail());

        return customerModelMapper.toCustomerTo(customerModel);
    }

    @Override
    public CustomerTo updatePatchCustomer(Integer customerId, CreateCustomerTo createCustomerTo) {
        Optional<CustomerModel> optionalCustomerModel = customerRepository.findById(customerId);

        if (optionalCustomerModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        CustomerModel customerModel = optionalCustomerModel.get();
        if (createCustomerTo.getName() != null) {
            customerModel.setName(createCustomerTo.getName());
        }

        if (createCustomerTo.getPhone() != null) {
            customerModel.setPhone(createCustomerTo.getPhone());
        }

        if (createCustomerTo.getEmail() != null) {
            customerModel.setEmail(createCustomerTo.getEmail());
        }

        return customerModelMapper.toCustomerTo(customerModel);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        CustomerModel customerModel = customerRepository.findById(customerId).orElse(null);
        if (customerModel == null) {
            throw new InstanceNotFoundException();
        }

        customerRepository.deleteById(customerId);
    }
}

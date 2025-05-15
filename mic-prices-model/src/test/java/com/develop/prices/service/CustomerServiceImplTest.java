package com.develop.prices.service;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.CustomerModelMapper;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    //anotación utilizada en frameworks de prueba, de la cual sirve para indicar que una clase o método debe ser reemplazado por un método "mock"
    private CustomerRepository customerRepository;


    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, Mappers.getMapper(CustomerModelMapper.class));
    }

    @Test
    void testFindAllWithFilters(){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        Pageable pageable = PageRequest.of(0,10);



        List<CustomerModel> listCustomerModel = List.of(customerModel);
        Page<CustomerModel> pageCustomerModel = new PageImpl<>(listCustomerModel,pageable,1);

        when(customerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageCustomerModel);

        PageResponseTo<CustomerTo> pageResponseTo = customerService.findAllWithFilters("Brais",999999999,"brais@example.com",pageable);

        assertNotNull(pageResponseTo);
        assertEquals(1, pageResponseTo.getTotalElements());
        assertEquals(1, pageResponseTo.getTotalPages());
        assertEquals(1, pageResponseTo.getContent().size());

        CustomerTo customerTo = pageResponseTo.getContent().get(0);
        assertEquals("Brais", customerTo.getName());
        assertEquals("brais@example.com",customerTo.getEmail());
        assertEquals(999999999, customerTo.getPhone());

    }

    @Test
    void testFindAllWithFiltersByName(){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        Pageable pageable = PageRequest.of(0,10);



        List<CustomerModel> listCustomerModel = List.of(customerModel);
        Page<CustomerModel> pageCustomerModel = new PageImpl<>(listCustomerModel,pageable,1);

        when(customerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageCustomerModel);

        PageResponseTo<CustomerTo> pageResponseTo = customerService.findAllWithFilters("Brais",null,null,pageable);

        assertNotNull(pageResponseTo);
        assertEquals(1, pageResponseTo.getTotalElements());
        assertEquals(1, pageResponseTo.getTotalPages());
        assertEquals(1, pageResponseTo.getContent().size());

        CustomerTo customerTo = pageResponseTo.getContent().get(0);
        assertEquals("Brais", customerTo.getName());
        assertEquals("brais@example.com",customerTo.getEmail());
        assertEquals(999999999, customerTo.getPhone());
    }

    @Test
    void testFindAllWithFiltersByEmail(){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        Pageable pageable = PageRequest.of(0,10);



        List<CustomerModel> listCustomerModel = List.of(customerModel);
        Page<CustomerModel> pageCustomerModel = new PageImpl<>(listCustomerModel,pageable,1);

        when(customerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageCustomerModel);

        PageResponseTo<CustomerTo> pageResponseTo = customerService.findAllWithFilters(null,null,"brais@example.com",pageable);

        assertNotNull(pageResponseTo);
        assertEquals(1, pageResponseTo.getTotalElements());
        assertEquals(1, pageResponseTo.getTotalPages());
        assertEquals(1, pageResponseTo.getContent().size());

        CustomerTo customerTo = pageResponseTo.getContent().get(0);
        assertEquals("Brais", customerTo.getName());
        assertEquals("brais@example.com",customerTo.getEmail());
        assertEquals(999999999, customerTo.getPhone());
    }

    @Test
    void testFindAllWithFiltersByPhone(){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        Pageable pageable = PageRequest.of(0,10);



        List<CustomerModel> listCustomerModel = List.of(customerModel);
        Page<CustomerModel> pageCustomerModel = new PageImpl<>(listCustomerModel,pageable,1);

        when(customerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageCustomerModel);

        PageResponseTo<CustomerTo> pageResponseTo = customerService.findAllWithFilters(null,999999999,null,pageable);

        assertNotNull(pageResponseTo);
        assertEquals(1, pageResponseTo.getTotalElements());
        assertEquals(1, pageResponseTo.getTotalPages());
        assertEquals(1, pageResponseTo.getContent().size());

        CustomerTo customerTo = pageResponseTo.getContent().get(0);
        assertEquals("Brais", customerTo.getName());
        assertEquals("brais@example.com",customerTo.getEmail());
        assertEquals(999999999, customerTo.getPhone());
    }


    @Test
    void testFindByCustomerId() {
        Integer customerId = 1;

        CustomerModel existingCustomerModel = new CustomerModel();
        existingCustomerModel.setCustomerId(customerId);
        existingCustomerModel.setName("Alice Johnson");
        existingCustomerModel.setEmail("alice@example.com");
        existingCustomerModel.setPhone(123123123);


        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomerModel));


        CustomerTo customerTo = customerService.findByCustomerId(customerId);

        assertNotNull(customerTo);
        assertEquals(customerId, customerTo.getCustomerId());
        assertEquals("Alice Johnson", customerTo.getName());
        assertEquals("alice@example.com", customerTo.getEmail());
        assertEquals(123123123, customerTo.getPhone());

        verify(customerRepository).findById(customerId);
    }

    @Test
    void testFindByCustomerIdNotFound() {
        Integer customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> customerService.findByCustomerId(customerId));
    }

    @Test
    void testSaveCustomer() {
        Integer customerId = 1;

        CreateCustomerTo createCustomerTo = new CreateCustomerTo();
        createCustomerTo.setName("Brais");
        createCustomerTo.setEmail("brais@example.com");
        createCustomerTo.setPhone(999999999);

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        when(customerRepository.save(any(CustomerModel.class))).thenReturn(customerModel);

        CustomerTo customerTo = customerService.saveCustomer(createCustomerTo);

        assertNotNull(customerTo);
        assertEquals(customerId, customerTo.getCustomerId());
        assertEquals("Brais", customerTo.getName());
        assertEquals("brais@example.com", customerTo.getEmail());
        assertEquals(999999999, customerTo.getPhone());


    }


    @Test
    void testSaveCustomerFailure() {

        CreateCustomerTo createCustomerTo = new CreateCustomerTo();
        createCustomerTo.setName("Brais");
        createCustomerTo.setEmail("brais@example.com");
        createCustomerTo.setPhone(999999999);

        CustomerModel customerModel = new CustomerModel();
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        when(customerRepository.save(customerModel)).thenThrow(new RuntimeException("Error to saved in database"));

        assertThrows(RuntimeException.class, () -> customerService.saveCustomer(createCustomerTo));

    }

    @Test
    void testUpdateCustomer() {
        Integer customerId = 1;

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        CustomerPutTo customerPutTo = new CustomerPutTo();
        customerPutTo.setName("Jorge");
        customerPutTo.setEmail("Jorge@example.com");
        customerPutTo.setPhone(888888888);


        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));

        CustomerTo updateCustomerTo = customerService.updateCustomer(customerId, customerPutTo);

        assertNotNull(updateCustomerTo);
        assertEquals(customerId, updateCustomerTo.getCustomerId());
        assertEquals("Jorge", updateCustomerTo.getName());
        assertEquals("Jorge@example.com", updateCustomerTo.getEmail());
        assertEquals(888888888, updateCustomerTo.getPhone());

        verify(customerRepository).findById(customerId);
    }


    @Test
    void testUpdateCustomerNotFound() {
        Integer customerId = 1;

        CustomerPutTo customerPutTo = new CustomerPutTo();
        customerPutTo.setName("Jorge");
        customerPutTo.setEmail("Jorge@example.com");
        customerPutTo.setPhone(888888888);
        ;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> customerService.updateCustomer(customerId, customerPutTo));
    }

    @Test
    void testUpdatePatchCustomer() {
        Integer customerId = 1;

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        CreateCustomerTo createCustomerTo = new CreateCustomerTo();
        createCustomerTo.setName("Jorge");
        createCustomerTo.setEmail("Jorge@example.com");
        createCustomerTo.setPhone(888888888);


        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));

        CustomerTo updateCustomerTo = customerService.updatePatchCustomer(customerId, createCustomerTo);

        assertNotNull(updateCustomerTo);
        assertEquals(customerId, updateCustomerTo.getCustomerId());
        assertEquals("Jorge", updateCustomerTo.getName());
        assertEquals("Jorge@example.com", updateCustomerTo.getEmail());
        assertEquals(888888888, updateCustomerTo.getPhone());

        verify(customerRepository).findById(customerId);
    }


    @Test
    void testUpdatePatchCustomerNotFound() {
        Integer customerId = 1;


        CreateCustomerTo createCustomerTo = new CreateCustomerTo();
        createCustomerTo.setName("Jorge");
        createCustomerTo.setEmail("Jorge@example.com");
        createCustomerTo.setPhone(888888888);


        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());


        assertThrows(InstanceNotFoundException.class, () -> customerService.updatePatchCustomer(customerId, createCustomerTo));
    }

    @Test
    void testDeleteCustomer() {
        Integer customerId = 1;
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);


        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);


    }

    @Test
    void testDeleteCustomerNotFound(){
        Integer customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
    }

}
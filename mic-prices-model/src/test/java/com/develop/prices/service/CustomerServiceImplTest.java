package com.develop.prices.service;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.CustomerModelMapper;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock //anotación utilizada en frameworks de prueba, de la cual sirve para indicar que una clase o método debe ser reemplazado por un método "mock"
    private CustomerRepository customerRepository;

    @Mock
    private CustomerModelMapper customerModelMapper = Mappers.getMapper(CustomerModelMapper.class);

    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository,customerModelMapper);
    }

    @Test
    public void testFindByCustomerId(){
        Integer customerId = 1;

        CustomerModel existingCustomerModel = new CustomerModel();
        existingCustomerModel.setCustomerId(customerId);
        existingCustomerModel.setName("Alice Johnson");
        existingCustomerModel.setEmail("alice@example.com");
        existingCustomerModel.setPhone(123123123);

        CustomerTo expectedCustomerTo = new CustomerTo();
        expectedCustomerTo.setCustomerId(customerId);
        expectedCustomerTo.setName(existingCustomerModel.getName());
        expectedCustomerTo.setEmail(existingCustomerModel.getEmail());
        expectedCustomerTo.setPhone(existingCustomerModel.getPhone());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomerModel));
        when(customerModelMapper.toCustomerTo(existingCustomerModel)).thenReturn(expectedCustomerTo);


        CustomerTo customerTo = customerService.findByCustomerId(customerId);

        assertNotNull(customerTo);
        assertEquals(customerId,customerTo.getCustomerId());
        assertEquals("Alice Johnson",customerTo.getName());
        assertEquals("alice@example.com", customerTo.getEmail());
        assertEquals(123123123, customerTo.getPhone());

        verify(customerRepository).findById(customerId);
        verify(customerModelMapper).toCustomerTo(existingCustomerModel);
    }

    @Test
    public void testFindByCustomerIdNotFound(){
        Integer customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> customerService.findByCustomerId(customerId));
    }

    @Test
    public void testSaveCustomer(){
        CreateCustomerTo createCustomerTo = new CreateCustomerTo();
        createCustomerTo.setName("Brais");
        createCustomerTo.setEmail("brais@example.com");
        createCustomerTo.setPhone(999999999);

        CustomerModel customerModel = new CustomerModel();
        customerModel.setName("Brais");
        customerModel.setEmail("brais@example.com");
        customerModel.setPhone(999999999);

        CustomerTo expectedCustomerTo = new CustomerTo();
        expectedCustomerTo.setName("Brais");
        expectedCustomerTo.setEmail("brais@example.com");
        expectedCustomerTo.setPhone(999999999);

        when(customerModelMapper.toCustomerModel(createCustomerTo)).thenReturn(customerModel);
        when(customerRepository.save(customerModel)).thenReturn(customerModel);
        when(customerModelMapper.toCustomerTo(customerModel)).thenReturn(expectedCustomerTo);

        CustomerTo customerTo = customerService.saveCustomer(createCustomerTo);

        assertNotNull(customerTo);
        assertEquals("Brais",customerTo.getName());
        assertEquals("brais@example.com",customerTo.getEmail());
        assertEquals(999999999,customerTo.getPhone());

        verify(customerModelMapper).toCustomerModel(createCustomerTo);
        verify(customerRepository).save(customerModel);
        verify(customerModelMapper).toCustomerTo(customerModel);
    }
    /// HACER EL SAVE FAILURE ///

    @Test
    public void testUpdateCustomer(){
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

        CustomerTo expectedCustomerTo = new CustomerTo();
        expectedCustomerTo.setCustomerId(customerId);
        expectedCustomerTo.setName(customerPutTo.getName());
        expectedCustomerTo.setEmail(customerPutTo.getEmail());
        expectedCustomerTo.setPhone(customerPutTo.getPhone());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));
        when(customerModelMapper.toCustomerTo(customerModel)).thenReturn(expectedCustomerTo);

        CustomerTo updateCustomerTo = customerService.updateCustomer(customerId,customerPutTo);

        assertNotNull(updateCustomerTo);
        assertEquals(customerId, updateCustomerTo.getCustomerId());
        assertEquals("Jorge", updateCustomerTo.getName());
        assertEquals("Jorge@example.com", updateCustomerTo.getEmail());
        assertEquals(888888888, updateCustomerTo.getPhone());

        verify(customerRepository).findById(customerId);
        verify(customerModelMapper).toCustomerTo(customerModel);
    }

    /// HACER EL PUT FAILURE ///


    @Test
    public void testUpdatePatchCustomer(){
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

        CustomerTo expectedCustomerTo = new CustomerTo();
        expectedCustomerTo.setCustomerId(customerId);
        expectedCustomerTo.setName(createCustomerTo.getName());
        expectedCustomerTo.setEmail(createCustomerTo.getEmail());
        expectedCustomerTo.setPhone(createCustomerTo.getPhone());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));
        when(customerModelMapper.toCustomerTo(customerModel)).thenReturn(expectedCustomerTo);

        CustomerTo updateCustomerTo = customerService.updatePatchCustomer(customerId,createCustomerTo);

        assertNotNull(updateCustomerTo);
        assertEquals(customerId, updateCustomerTo.getCustomerId());
        assertEquals("Jorge", updateCustomerTo.getName());
        assertEquals("Jorge@example.com", updateCustomerTo.getEmail());
        assertEquals(888888888, updateCustomerTo.getPhone());

        verify(customerRepository).findById(customerId);
        verify(customerModelMapper).toCustomerTo(customerModel);
    }

    /// FALTA EL FAILURE DE PATCH///


    @Test
    public void testDeleteCustomer(){
        Integer customerId = 1;
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);


        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerModel));

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);


    }

    /// HACER EL FAILURE DE DELETE ///

}
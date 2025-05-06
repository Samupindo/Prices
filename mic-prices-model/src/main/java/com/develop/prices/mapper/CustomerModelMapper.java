package com.develop.prices.mapper;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.entity.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerModelMapper {

    CustomerDTO customerModelToCustomerDTO (CustomerModel customerModel);

    List<CustomerDTO> toCustomerTo(List<CustomerModel> customerModels);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CreateCustomerDTO createCustomerDTO);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel (CustomerPutDTO customerPutDTO);

    @Mapping(target = "purchases",ignore = true)
    CustomerModel toCustomerModel(CustomerDTO customerDTO);

    default CustomerModel map(Integer customerId){
        if(customerId == null){
            return null;
        }

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);
        return customerModel;
    }

}

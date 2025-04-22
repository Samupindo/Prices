package com.develop.prices.mapper;

import com.develop.prices.model.CustomerModel;
import org.mapstruct.Mapper;
import com.develop.prices.model.dto.CustomerDTO;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerModelToCustomerDTO (CustomerModel customerModel);
}

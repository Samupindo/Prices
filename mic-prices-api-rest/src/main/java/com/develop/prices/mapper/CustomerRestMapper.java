package com.develop.prices.mapper;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {
    CustomerDTO toCustomerDTO(CustomerTo customerTo);

    CustomerPutTo toCustomerPutTo(CustomerPutDTO customerPutDTO);
    CreateCustomerTo toCreateCustomerTo(CreateCustomerDTO createCustomerDTO);

}

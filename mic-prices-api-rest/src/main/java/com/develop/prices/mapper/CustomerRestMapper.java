package com.develop.prices.mapper;

import com.develop.prices.dto.CreateCustomerDTO;
import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.dto.CustomerPutDTO;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface CustomerRestMapper {
    CustomerDTO toCustomerDTO(CustomerTo customerTo);

    List<CustomerDTO> toListCustomerDTO(List<CustomerTo> customerTo);

//    List<CustomerDTO> toListCustomerDTO(PageResponse<CustomerTo> customerTo);

    CustomerPutTo toCustomerPutTo(CustomerPutDTO customerPutDTO);

    CreateCustomerTo toCreateCustomerTo(CreateCustomerDTO createCustomerDTO);

}

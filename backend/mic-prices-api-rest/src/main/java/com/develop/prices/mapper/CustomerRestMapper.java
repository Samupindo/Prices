package com.develop.prices.mapper;

import com.develop.prices.dto.CreateCustomerDto;
import com.develop.prices.dto.CustomerDto;
import com.develop.prices.dto.CustomerPutDto;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {
  CustomerDto toCustomerDto(CustomerTo customerTo);

  CustomerPutTo toCustomerPutTo(CustomerPutDto customerPutDto);

  CreateCustomerTo toCreateCustomerTo(CreateCustomerDto createCustomerDto);
}

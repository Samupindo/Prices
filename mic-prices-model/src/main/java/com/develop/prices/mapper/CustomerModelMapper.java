package com.develop.prices.mapper;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface CustomerModelMapper {

    CustomerTo toCustomerTo(CustomerModel customerModel);

    PageResponse<CustomerTo> toCustomerTo(PageResponse<CustomerTo> pageResponse);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CreateCustomerTo createCustomerTo);



}

package com.develop.prices.mapper;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerTo;
import com.develop.prices.to.PageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface CustomerModelMapper {

    CustomerTo toCustomerTo(CustomerModel customerModel);

    PageResponseTo<CustomerTo> toCustomerTo(PageResponseTo<CustomerTo> pageResponseTo);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CreateCustomerTo createCustomerTo);



}

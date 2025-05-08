package com.develop.prices.mapper;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.to.CreateCustomerTo;
import com.develop.prices.to.CustomerPutTo;
import com.develop.prices.to.CustomerTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerModelMapper {

    CustomerTo toCustomerTo(CustomerModel customerModel);

    List<CustomerTo> toCustomerTo(List<CustomerModel> customerModels);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CreateCustomerTo createCustomerTo);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CustomerPutTo customerPutTo);

    @Mapping(target = "purchases", ignore = true)
    CustomerModel toCustomerModel(CustomerTo customerTo);

    default CustomerModel map(Integer customerId) {
        if (customerId == null) {
            return null;
        }

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerId);
        return customerModel;
    }

}

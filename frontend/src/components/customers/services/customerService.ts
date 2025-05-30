import axiosInstance from "../../../config/api-customer";
import type { PageResponse, CustomerDto, CreateCustomerDto, CustomerPutDto } from "../types/customer";


export const getCustomers = async () => {
    const response = await axiosInstance.get<PageResponse<CustomerDto>>("/customers");
    return response.data.content;
}

export const getCustomerById = async (customerId: number) => {
    const response = await axiosInstance.get<CustomerDto>(`/customers/${customerId}`);
    return response.data;
}

export const createCustomer = async (customer: CreateCustomerDto) => {
    const response = await axiosInstance.post<CustomerDto>("/customers", customer);
    return response.data;
}

export const updateCustomer = async (customerId: number, customer: CustomerPutDto) => {
    const response = await axiosInstance.put(`/customers/${customerId}`, customer);
    return response.data;
}

export const deleteCustomer = async (customerId: number) => {
    const response = await axiosInstance.delete(`/customers/${customerId}`);
    return response.data;
}
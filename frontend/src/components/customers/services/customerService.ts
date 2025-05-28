import axiosInstance from "../../../config/api-customer";
import type { PageResponse, CustomerDto } from "../types/customer";


export const getCustomers = async () => {
    const response = await axiosInstance.get<PageResponse<CustomerDto>>("/customers");
    return response.data.content;
}

export const getCustomerById = async (customerId: number) => {
    const response = await axiosInstance.get<CustomerDto>(`/customers/${customerId}`);
    return response.data;
}
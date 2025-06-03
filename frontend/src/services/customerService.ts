import axiosInstance from "../lib/api/apiFacade";
import type { PageResponse, CustomerDto, CreateCustomerDto, CustomerPutDto } from "../types/customer";

export interface CustomerFilters {
    name?: string;
    email?: string;
    phone?: number;
}

export interface PaginatedRequest {
    page: number;
    size: number;
    filters?: CustomerFilters;
}


export const getCustomers = async ({ page = 0, size = 10, filters }: PaginatedRequest) => {
    const queryParams = new URLSearchParams({
        page: page.toString(),
        size: size.toString()
    });

    if (filters?.name) queryParams.append('name', filters.name);
    if (filters?.email) queryParams.append('email', filters.email);
    if (filters?.phone) queryParams.append('phone', filters.phone.toString());

    const response = await axiosInstance.get<PageResponse<CustomerDto>>(`/customers?${queryParams}`);
    return response.data;
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
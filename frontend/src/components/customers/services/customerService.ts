import axios, { AxiosError } from "axios";
import { CustomerPutDto, CustomerDto, CreateCustomerDto } from "../types/Customer";
import { PageResponse } from "../types/Customer";

export class CustomerService {
    private apiURL = 'http://localhost:8080/customers';
    
    constructor() {
        axios.defaults.headers.common['Accept'] = 'application/json';
        axios.defaults.headers.common['Content-Type'] = 'application/json';
    }

    public async getCustomers(): Promise<PageResponse<CustomerDto>> {
        try {
            const response = await axios.get<PageResponse<CustomerDto>>(this.apiURL);
            console.log('API Response:', response.data); // Debug log
            return response.data;
        } catch (error) {
            if (error instanceof AxiosError) {
                console.error("API Error:", {
                    message: error.message,
                    status: error.response?.status,
                    data: error.response?.data
                });
            }
            throw error;
        }
    }

    public async post(data: CreateCustomerDto) {
        try {
            const response = await axios.post<CustomerDto>(this.apiURL, data);
            return response.data;
        } catch (error) {
            console.error("Error posting customer:", error);
            throw error;
        }
    }

    public async getCustomerById(customerId: number) {
        try {
            const response = await axios.get<CustomerDto>(`${this.apiURL}/${customerId}`);
            return response.data;
        } catch (error) {
            console.error("Error fetching customer by ID:", error);
            throw error;
        }
    }

    public async put(data: CustomerPutDto) {
        try {
            const response = await axios.put<CustomerDto>(`${this.apiURL}/${data.customerId}`, data);
            return response.data;
        } catch (error) {
            console.error("Error updating customer:", error);
            throw error;
        }
    }

    public async delete(customerId: number) {
        try {
            const response = await axios.delete(`${this.apiURL}/${customerId}`);
            return response.data;
        } catch (error) {
            console.error("Error deleting customer:", error);
            throw error;
        }
    }
    
}
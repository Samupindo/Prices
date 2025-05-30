export interface CustomerPutDto {
    name: string,
    email: string,
     phone: number,
}

export interface CustomerDto {
    customerId: number,
    name: string,
    phone: number,
    email: string,
}

export interface CreateCustomerDto {
    name: string,
    phone: number,
    email: string,
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
}
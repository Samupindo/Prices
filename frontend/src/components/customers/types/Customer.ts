export interface CustomerPutDto {
    customerId: number | null,
    name: string,
    phone: number,
    email: string,
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
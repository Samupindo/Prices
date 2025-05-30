import type { PageResponseDto, ProductWithShopsDto, ProductNameDto } from "../types/products";
import axiosInstance from "../lib/api/apiFacade";

interface ProductFilters {
    name?: string;
    priceMin?: number;
    priceMax?: number;
    page?: number;
    size?: number;
}

export const getProducts = async (filters?: ProductFilters): Promise<PageResponseDto<ProductWithShopsDto>> => {
    try {
        const response = await axiosInstance.get("/products", {
            params: {
                ...filters,
                page: filters?.page || 0,
                size: filters?.size || 10
            }
        });
        return response.data;
    } catch (error) {
        throw new Error('Failed to fetch products');
    }
};

export const getProductById = async (productId: number) => {
    try {
        const response = await axiosInstance.get(`/products/${productId}`);
        return response.data;
    } catch (error) {
        throw new Error('Failed to fetch product');
    }
};
export const createProduct = async (product: ProductNameDto) => {
    try {
        const response = await axiosInstance.post("/products", product);
        console.log('Product created successfully:', response.data);
        return response.data;
    } catch (error: any) {
        // Agregar más detalles al error
        const errorDetails = error.response?.data;
        console.error('Backend error details:', errorDetails);
        throw new Error(errorDetails?.message || error.message || 'Failed to create product');
    }
};

export const updateProduct = async (productId: number, product: ProductNameDto) => {
    try {
        const response = await axiosInstance.put(`/products/${productId}`, product);
        console.log('Product updated successfully:', response.data);
        return response.data;
    } catch (error: any) {
        const errorDetails = error.response?.data;
        console.error('Backend error details:', errorDetails);
        throw new Error(errorDetails?.message || error.message || 'Failed to update product');
    }
};

export const deleteProduct = async(productId: number) =>{
    try {
        const response = await axiosInstance.delete(`/products/${productId}`);
        console.log('Product deleted successfully:', response.data);
        return response.data;
    } catch (error: any) {
        const errorDetails = error.response?.data;
        console.error('Backend error details:', errorDetails);
        throw new Error(errorDetails?.message || error.message || 'Failed to delete product');
    }
}
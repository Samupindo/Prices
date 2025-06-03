import type { PageResponseDto, ProductWithShopsDto, ProductNameDto } from "../types/products";
import axiosInstance from "../lib/api/apiFacade";

interface ProductFilters {
    name?: string;
    priceMin?: number;
    priceMax?: number;
}
export interface PageResponse{
    page: number;
    size: number;
    filters?: ProductFilters;
}
export const getProducts = async ({page = 1, size = 10, filters}: PageResponse) => {
    try {
        // Convertimos la página de frontend (1-based) a backend (0-based)
        const backendPage = page - 1;
        
        const response = await axiosInstance.get("/products", {
            params: {
                ...filters,
                page: backendPage,
                size
            }
        });
        return response.data;
    } catch (error) {
        throw error;
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
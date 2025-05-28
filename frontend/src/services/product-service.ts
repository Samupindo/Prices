import type { PageResponseDto, ProductWithShopsDto } from "../types/shops";
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

export const getProductById = (productId: number) => axiosInstance.get(`/products/${productId}`);

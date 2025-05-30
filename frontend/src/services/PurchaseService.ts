import axiosInstance from "../lib/api/apiFacade";
import type { PageResponseDto, PurchaseDto } from "../types/purchase";

interface PurchaseFilters {
    customerId?: number;
    productInShop?: number[];
    totalPriceMax?: number;
    totalPriceMin?: number;
    shopping?: boolean;
    page?: number;
    size?: number;
}

export const getPurchases = async (filters? : PurchaseFilters): Promise<PageResponseDto<PurchaseDto>> => {
    try {
        const response = await axiosInstance.get("/purchases", {
            params: {
                ...filters,
                page: filters?.page || 0,
                size: filters?.size || 10
            }
        });
        return response.data;
    } catch (error) {
        throw new Error('Failed to fetch purchases');
    }
}

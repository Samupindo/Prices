import axiosInstance from "../lib/api/apiFacade";
import type { PageResponseDto, PostPurchaseDto, PurchaseDto } from "../types/purchase";

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

export const getPurchaseById = async (purchaseId: number) => {
    try {
        const response = await axiosInstance.get(`/purchases/${purchaseId}`);
        return response.data;
    } catch (error) {
        throw new Error('Failed to fetch purchase');
    }
}

export const createPurchase = async (purchase: PostPurchaseDto) => {
    try {
        const response = await axiosInstance.post("/purchases", purchase);
        console.log('Purchase created successfully:', response.data);
        return response.data;
    } catch (error) {
        throw new Error('Failed to create purchase');
    }
}

export const deletePurchase = async (purchaseId: number) => {
    try {
        const response = await axiosInstance.delete(`/purchases/${purchaseId}`);
        console.log('Purchase deleted successfully:', response.data);
        return response.data;
    } catch (error) {
        throw new Error('Failed to delete purchase');
    }
}

export const finishPurchase = async (purchaseId: number) => {
    try {
        const response = await axiosInstance.patch(`/purchases/${purchaseId}/finish`);
        console.log('Purchase finished successfully:', response.data);
        return response.data;
    } catch (error) {
        throw new Error('Failed to finish purchase');
    }
}

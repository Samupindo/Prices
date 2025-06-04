import axiosInstance from "../lib/api/apiFacade";
import type { PageResponseDto, PostPurchaseDto, PurchaseDto } from "../types/Purchase";

interface PurchaseFilters {
    customerId?: number;
    productInShop?: number[];
    totalPriceMax?: number;
    totalPriceMin?: number;
    shopping?: boolean;
    page?: number;
    size?: number;
}

interface PageResponse {
    page: number;
    size: number;
    filters?: PurchaseFilters;
}

export const getPurchases = async ({page = 1, size = 10, filters}: PageResponse): Promise<PageResponseDto<PurchaseDto>> => {
    try {
        // Convertimos la página de frontend (1-based) a backend (0-based)
        const backendPage = page - 1;
        
        const response = await axiosInstance.get("/purchases", {
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
    if (isNaN(purchaseId)) {
        throw new Error('Purchase ID is required and must be a valid number');
    }
    try {
        const response = await axiosInstance.delete(`/purchases/${purchaseId}`);
        console.log('Purchase deleted successfully:', response.data);
        return response.data;
    } catch (error) {
        throw error;
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

export const addProductToPurchase = async (purchaseId: number, productInShopId: number) => {
    try {
        const response = await axiosInstance.post(`/purchases/${purchaseId}/productInShop/${productInShopId}`);
        console.log('Product in shop added to purchase successfully:', response.data);
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data?.message || 
                           error.message || 
                           `Failed to add product in shop with ID ${productInShopId} to purchase with ID ${purchaseId}. ` +
                           `Please verify that the product in shop exists and is available for purchase.`;
        console.error('Error details:', error);
        throw new Error(errorMessage);
    }
}

export const deleteProductFromPurchase = async (purchaseId: number, productInShopId: number) => {
    try {
        const response = await axiosInstance.delete(`/purchases/${purchaseId}/productInShop/${productInShopId}`);
        console.log('Product in shop deleted from purchase successfully:', response.data);
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data?.message || 
                           error.message || 
                           `Failed to delete product in shop with ID ${productInShopId} from purchase with ID ${purchaseId}. ` +
                           `Please verify that the product in shop exists and is available for purchase.`;
        console.error('Error details:', error);
        throw new Error(errorMessage);
    }
}

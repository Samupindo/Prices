import type { ShopAddDto, PageResponseDto, ShopDto, ShopPutDto, AddProductShopDto, ProductInShopPatchDto } from "../types/Shops";
import axiosInstance from "../lib/api/apiFacade";

export interface ShopFilter {
    country?: string;
    city?: string;
    address?: string;
}

export interface PageResponse{
    page: number;
    size: number;
    filters?: ShopFilter;
}

export const getShops = async ({page = 0, size = 0, filters}: PageResponse) => {
    const params = new URLSearchParams({
        page: page.toString(),
        size: size.toString()
    });
    if(filters?.country){
        params.append("country", filters.country);
    }
    if(filters?.city){
        params.append("city", filters.city);
    }
    if(filters?.address){
        params.append("address", filters.address);
    }

    const response = await axiosInstance.get<PageResponseDto<ShopDto>>(`/shops?${params}`);
    return response.data;
}

export const getShopById = async (shopId: string) => {
    const response = await axiosInstance.get<ShopDto>(`/shops/${shopId}`);
    return response.data;
}

export const createShop = async (shopAddDto: ShopAddDto) => {
    const response = await axiosInstance.post("/shops", shopAddDto);
    return response.data;
}

export const addProductToShop = async (shopId: string, productId: string, addProductToShop: AddProductShopDto ) => {
    const response = await axiosInstance.post(`/shops/${shopId}/products/${productId}`, addProductToShop);
    return response.data;
}

export const updateShop = async (shopId: string, shopPutDto: ShopPutDto) => {
    const response = await axiosInstance.put(`/shops/${shopId}`, shopPutDto);
    return response.data;
}

export const updateProductInShop = async (shopId: string, productId: string, productInShopPatchDto: ProductInShopPatchDto) => {
    const response = await axiosInstance.patch(`/shops/${shopId}/products/${productId}`, productInShopPatchDto);
    return response.data;
}

export const deleteShop = async (shopId: string) => {
    const response = await axiosInstance.delete(`/shops/${shopId}`)
    return response.data;
}

export const deleteProductFromShop = async (shopId: string, productId: string) => {
    const response = await axiosInstance.delete(`/shops/${shopId}/products/${productId}`);
    return response.data;
}

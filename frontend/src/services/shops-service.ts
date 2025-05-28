import type { PageResponseDto, ShopDto } from "../types/shops";
import axiosInstance from "../lib/api/apiFacade";



export const getShops = async () => {
    const response = await axiosInstance.get<PageResponseDto<ShopDto>>("/shops")
    return response.data.content;
}

export const getShopById = async (shopId: string) => {
    const response = await axiosInstance.get<ShopDto>(`/shops/${shopId}`);
    return response.data;
}

export const createShop = (shopData: any) => axiosInstance.post("/shops", shopData);
export const updateShop = (shopId: string, shopData: any) => axiosInstance.put(`/shops/${shopId}`, shopData);
export const deleteShop = (shopId: string) => axiosInstance.delete(`/shops/${shopId}`);



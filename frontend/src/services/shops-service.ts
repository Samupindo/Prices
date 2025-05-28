import type { PageResponseDto, ShopDto } from "../types/shops";
import axiosInstance from "../lib/api/apiFacade";



export const getShops = async () => {
    const response = await axiosInstance.get<PageResponseDto<ShopDto>>("/shops")
    return response.data.content;
}

export const getShopById = (userId: string) => axiosInstance.get(`/shops/${userId}`);
export const createShop = (shopData: any) => axiosInstance.post("/shops", shopData);
export const updateShop = (userId: string, shopData: any) => axiosInstance.put(`/shops/${userId}`, shopData);
export const deleteShop = (userId: string) => axiosInstance.delete(`/shops/${userId}`);



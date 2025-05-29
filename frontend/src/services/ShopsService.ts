import type { ShopAddDto, PageResponseDto, ShopDto, ShopPutDto } from "../types/shops";
import axiosInstance from "../lib/api/ApiFacade";

export const getShops = async () => {
    const response = await axiosInstance.get<PageResponseDto<ShopDto>>("/shops")
    return response.data.content;
}

export const getShopById = async (shopId: string) => {
    const response = await axiosInstance.get<ShopDto>(`/shops/${shopId}`);
    return response.data;
}

export const createShop = async (shopAddDto: ShopAddDto) => {
    const response = await axiosInstance.post("/shops", shopAddDto);
    return response.data;
}

export const updateShop = async (shopId: string, shopPutDto: ShopPutDto) => {
    const response = await axiosInstance.put(`/shops/${shopId}`, shopPutDto);
    return response.data;
}

export const deleteShop = async (shopId: string) => {
    const response = await axiosInstance.delete(`/shops/${shopId}`)
    return response.data;
}

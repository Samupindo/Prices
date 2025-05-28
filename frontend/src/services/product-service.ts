import type { PageResponseDto, ProductWithShopsDto, ShopDto } from "../types/shops";
import axiosInstance from "../lib/api/apiFacade";


export const getProducts = async () => {
    const response = await axiosInstance.get<PageResponseDto<ProductWithShopsDto>>("/products")
    return response.data.content;
}

export const getProductById = (productId: number) => axiosInstance.get(`/products/${productId}`);
// export const createProduct = (shopData: any) => axiosInstance.post("/product", shopData);
// export const updateProduct = (userId: string, shopData: any) => axiosInstance.put(`/product/${userId}`, shopData);
// export const deleteProduct = (userId: string) => axiosInstance.delete(`/product/${userId}`);

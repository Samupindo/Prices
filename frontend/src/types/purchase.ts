import type { CustomerDto } from "../components/customers/types/Customer";
import type { ProductInShopDto } from "./shops";

export interface PurchaseDto{
    purchaseId :number,
    customer: CustomerDto,
    products: ProductInShopDto[],
    totalPrice: number,
    shopping: boolean

}

export interface PostPurchaseDto{
    customerId: number
}

export interface PageResponseDto<T>{
        content: T[];
        totalElements: number;
        totalPages: number;

}
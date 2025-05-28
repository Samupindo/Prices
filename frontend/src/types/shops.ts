
export interface ShopDto {
    shopId: number;
    country: string;
    city: string;
    address: string;
  }
  
  export interface ShopAddDto {
    country: string;
    city: string;
    address: string;
  }
  
  export interface ShopPutDto {
    country: string;
    city: string;
    address: string;
  }
  
  export interface UpdateShopDto {
    country?: string;
    city?: string;
    address?: string;
  }
  
  export interface ProductInShopDto {
    productInShopId: number;
    productId: number;
    shopId: number;
    price: number;
  }
  
  export interface AddProductShopDto {
    price: number;
  }
  
  export interface ProductInShopPatchDto {
    price: number;
  }
  
  export interface ProductDto {
    productId: number;
    name: string;
  }
  
  export interface ShopInfoDto {
    productInShopId: number;
    shopId: number;
    price: number;
  }
  
  export interface ProductWithShopsDto {
    productId: number;
    name: string;
    shop: ShopInfoDto[];
  }
  
  export interface PageResponseDto<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
  }
  
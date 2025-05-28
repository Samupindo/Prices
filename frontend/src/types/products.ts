export interface ProductDto {
  productId: number;
  name: string;
}

export interface ProductNameDto {
  name: string;
}

export interface ProductPutDto {
    name: string,
}

export interface ProductWithShopsDto {
  productId: number;
  name: string;
  shop: ShopInfoDto[];
}

export interface ShopInfoDto {
  productInShopId: number;
  shopId: number;
  price: number;
}

export interface PageResponseDto<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
}
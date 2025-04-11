package com.develop.prices.modelo.dto;

import java.util.List;

public class ProductWithShopsDTO {
    private Integer productId;
    private String name;
    private List<ShopInfoDTO> shop;

    public ProductWithShopsDTO(Integer productId, String name, List<ShopInfoDTO> shop) {
        this.productId = productId;
        this.name = name;
        this.shop = shop;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public List<ShopInfoDTO> getShop() {
        return shop;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShop(List<ShopInfoDTO> shop) {
        this.shop = shop;
    }
}
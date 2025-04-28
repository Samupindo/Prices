package com.develop.prices.dto;

import java.math.BigDecimal;

public class ShopInfoDTO {
    private Integer shopProductInfoId;
    private Integer shopId;
    private BigDecimal price;

    public ShopInfoDTO() {
    }

    public ShopInfoDTO(Integer shopProductInfoId, Integer shopId, BigDecimal price) {
        this.shopProductInfoId = shopProductInfoId;
        this.shopId = shopId;
        this.price = price;
    }

    public ShopInfoDTO(Integer shopId, BigDecimal price) {
        this.shopId = shopId;
        this.price = price;
    }

    public Integer getShopId() {
        return shopId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getShopProductInfoId() {
        return shopProductInfoId;
    }

    public void setShopProductInfoId(Integer shopProductInfoId) {
        this.shopProductInfoId = shopProductInfoId;
    }

    @Override
    public String toString() {
        return "ShopInfoDTO{" +
                "shopId=" + shopId +
                ", price=" + price +
                '}';
    }

}

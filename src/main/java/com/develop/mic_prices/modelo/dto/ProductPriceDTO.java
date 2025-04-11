package com.example.projectProducts.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductPriceDTO implements Serializable {

    private Integer productId;
    private Integer shopId;
    private BigDecimal price;

    public ProductPriceDTO() {
    }

    public ProductPriceDTO(Integer productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public ProductPriceDTO(Integer productId, Integer shopId, BigDecimal price) {
        this.productId = productId;
        this.shopId = shopId;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ProductPriceModel{" +
                "productId=" + productId +
                ", price=" + price +
                '}';
    }
}

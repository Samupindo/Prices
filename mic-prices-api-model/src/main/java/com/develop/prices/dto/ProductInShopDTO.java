package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInShopDTO implements Serializable {

    private Integer productInShopId;
    private Integer productId;
    private Integer shopId;
    private BigDecimal price;

    public ProductInShopDTO() {
    }

    public ProductInShopDTO(Integer productInShopId, Integer productId, Integer shopId, BigDecimal price) {
        this.productInShopId = productInShopId;
        this.productId = productId;
        this.shopId = shopId;
        this.price = price;
    }

    public Integer getProductInShopId() {
        return productInShopId;
    }

    public void setProductInShopId(Integer productInShopId) {
        this.productInShopId = productInShopId;
    }

    public Integer getProductId() {
        return productId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductInShopTo{" +
                "productInShopId=" + productInShopId +
                "productId=" + productId +
                ", shopId=" + shopId +
                ", price=" + price +
                '}';
    }
}

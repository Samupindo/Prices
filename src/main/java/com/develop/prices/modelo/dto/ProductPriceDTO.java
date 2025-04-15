package com.develop.prices.modelo.dto;

import com.develop.prices.modelo.ProductModel;
import com.develop.prices.modelo.ShopModel;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductPriceDTO implements Serializable {

    private ProductModel productId;
    private ShopModel shopId;
    private BigDecimal price;

    public ProductPriceDTO() {
    }

    public ProductPriceDTO(ProductModel productId, ShopModel shopId, BigDecimal price) {
        this.productId = productId;
        this.shopId = shopId;
        this.price = price;
    }

    public ProductModel getProductId() {
        return productId;
    }

    public void setProductId(ProductModel productId) {
        this.productId = productId;
    }

    public ShopModel getShopId() {
        return shopId;
    }

    public void setShopId(ShopModel shopId) {
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
        return "ProductPriceDTO{" +
                "productId=" + productId +
                ", shopId=" + shopId +
                ", price=" + price +
                '}';
    }
}

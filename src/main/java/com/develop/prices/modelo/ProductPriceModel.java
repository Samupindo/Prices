package com.develop.prices.modelo;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "productPrices")
public class ProductPriceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    private Integer productId;

    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "shopId", nullable = false)
    private ShopModel shop;

    public ProductPriceModel() {
    }

    public ProductPriceModel(BigDecimal price, Integer productId) {
        this.price = price;
        this.productId = productId;
    }

    public ProductPriceModel(ProductModel product, ShopModel shop, BigDecimal price) {
        this.product = product;
        this.shop = shop;
        this.price = price;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Integer getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPriceModel{" +
                "shopId=" + shopId +
                ", productId=" + productId +
                ", price=" + price +
                '}';
    }
}


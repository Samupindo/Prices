package com.develop.prices.modelo;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "productPrices")
public class ProductPriceModel implements Serializable {



    private BigDecimal price;

    @Id
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private ProductModel product;

    @Id
    @ManyToOne
    @JoinColumn(name = "shopId", nullable = false)
    private ShopModel shop;

    public ProductPriceModel() {
    }

    public ProductPriceModel(BigDecimal price, ProductModel product, ShopModel shop) {
        this.price = price;
        this.product = product;
        this.shop = shop;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public ShopModel getShop() {
        return shop;
    }

    public void setShop(ShopModel shop) {
        this.shop = shop;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPriceModel{" +
                "price=" + price +
                ", product=" + product +
                ", shop=" + shop +
                '}';
    }
}


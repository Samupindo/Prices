package com.develop.prices.model;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product_price")
public class ProductPriceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productPriceId;

    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;


    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopModel shop;

    public ProductPriceModel() {
    }

    public ProductPriceModel( BigDecimal price, ProductModel product, ShopModel shop) {
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

    public Integer getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(Integer productPriceId) {
        this.productPriceId = productPriceId;
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


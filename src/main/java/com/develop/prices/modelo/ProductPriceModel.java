package com.develop.prices.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
public class ProductPriceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private BigDecimal price;

    public ProductPriceModel() {
    }

    public ProductPriceModel(BigDecimal price, Integer productId) {
        this.price = price;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                '}';
    }
}


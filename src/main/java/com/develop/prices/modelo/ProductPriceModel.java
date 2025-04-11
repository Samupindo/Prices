package com.develop.prices.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="product_prices")
public class ProductPriceModel implements  Serializable {

    @Id
    private  Integer  productId;
    private BigDecimal price;

    public ProductPriceModel() {
    }

    public ProductPriceModel(BigDecimal price,Integer productId) {
        this.price = price;
        this.productId = productId;
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

    @Override
    public String toString() {
        return "ProductPriceModel{" +
                "productId=" + productId +
                ", price=" + price +
                '}';
    }
}


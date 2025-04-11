package com.develop.mic_prices.modelo;

import  java.io.Serializable;
import java.math.BigDecimal;

public class ProductPriceModel implements  Serializable {

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


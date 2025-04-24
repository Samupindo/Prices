package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Function;

public class ProductPricePatchDTO implements Serializable {

    private BigDecimal price;

    public ProductPricePatchDTO() {
    }

    public ProductPricePatchDTO(BigDecimal price) {
        this.price = price;

    }


    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "ProductPriceModel{" +
                ", price=" + price +
                '}';
    }
}


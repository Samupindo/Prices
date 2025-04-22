package com.develop.prices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductPricePatchDTO implements Serializable {

    @NotNull
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


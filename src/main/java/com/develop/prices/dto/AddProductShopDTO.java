package com.develop.prices.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopDTO implements  Serializable { //este DTO y el de ProductPricePatchDTO son lo mismo

    @Positive
    @DecimalMin(value="0.01")
    @DecimalMax(value = "999999999.99")
    @NotNull
    private BigDecimal price;

    public AddProductShopDTO() {
    }

    public AddProductShopDTO(BigDecimal price) {
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


package com.develop.prices.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopDTO implements  Serializable { //este DTO y el de ProductPricePatchDTO son lo mismo

    @Positive(message = "The price must be positive")
    @DecimalMin(value="0.01", message = "The minimum price must be 0.01")
    @DecimalMax(value = "999999999.99", message = "The maximum price must be 999999999")
    @NotBlank(message = "The field price cannot be empty")
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


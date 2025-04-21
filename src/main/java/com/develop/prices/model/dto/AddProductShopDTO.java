package com.develop.prices.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopDTO implements  Serializable { //este DTO y el de ProductPricePatchDTO son lo mismo

    @NotBlank(message = "El campo price no debe ir vacío")
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


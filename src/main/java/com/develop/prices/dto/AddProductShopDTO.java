package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopDTO implements  Serializable { //este DTO y el de ShopProductInfoPatchDTO son lo mismo

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
        return "ShopProductInfoModel{" +
                ", price=" + price +
                '}';
    }
}


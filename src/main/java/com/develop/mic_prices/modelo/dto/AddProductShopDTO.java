package com.example.projectProducts.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopDTO implements  Serializable {

    private BigDecimal price;

    public AddProductShopDTO() {
    }

    public AddProductShopDTO(BigDecimal price, String locationId, int productId) {
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


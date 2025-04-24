package com.develop.prices.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class PurchaseProductDTO {

    private Set<ProductPriceDTO> info;


    public PurchaseProductDTO() {
    }

    public PurchaseProductDTO( Set<ProductPriceDTO> info) {
        this.info = info;
    }

    public Set<ProductPriceDTO> getInfo() {
        return info;
    }

    public void setInfo(Set<ProductPriceDTO> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PurchaseProductDTO{" +
                "info=" + info +
                '}';
    }
}

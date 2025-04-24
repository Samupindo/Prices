package com.develop.prices.dto;

import com.develop.prices.model.ProductPriceModel;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseDTO {

    private Integer purchaseId;
    private CustomerDTO customer;
    private List<ProductPriceDTO> info;
    private BigDecimal totalPrice;

    public PurchaseDTO() {
    }

    public PurchaseDTO(CustomerDTO customer, List<ProductPriceDTO> info, BigDecimal totalPrice) {
        this.customer = customer;
        this.info = info;
        this.totalPrice = totalPrice;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ProductPriceDTO> getInfo() {
        return info;
    }

    public void setInfo( List<ProductPriceDTO> info) {
        this.info = info;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", info=" + info +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

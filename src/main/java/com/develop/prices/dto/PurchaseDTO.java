package com.develop.prices.dto;

import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ProductPriceModel;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

public class PurchaseDTO {

    private Integer purchaseId;
    private CustomerModel customer;
    private Set<ProductPriceModel> info;
    private BigDecimal totalPrice;

    public PurchaseDTO() {
    }

    public PurchaseDTO(CustomerModel customer, Set<ProductPriceModel> info, BigDecimal totalPrice) {
        this.customer = customer;
        this.info = info;
        this.totalPrice = totalPrice;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public Set<ProductPriceModel> getInfo() {
        return info;
    }

    public void setInfo(Set<ProductPriceModel> info) {
        this.info = info;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}

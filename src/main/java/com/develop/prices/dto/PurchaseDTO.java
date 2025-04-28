package com.develop.prices.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseDTO {

    private Integer purchaseId;
    private CustomerDTO customer;
    private List<ShopProductInfoDTO> products;
    private BigDecimal totalPrice;

    public PurchaseDTO() {
    }

    public PurchaseDTO(CustomerDTO customer, List<ShopProductInfoDTO> products, BigDecimal totalPrice) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public PurchaseDTO(Integer purchaseId, CustomerDTO customer, BigDecimal totalPrice) {
        this.purchaseId = purchaseId;
        this.customer = customer;
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

    public List<ShopProductInfoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ShopProductInfoDTO> products) {
        this.products = products;
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
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

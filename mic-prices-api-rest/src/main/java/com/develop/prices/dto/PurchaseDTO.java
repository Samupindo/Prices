package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseDTO implements Serializable {

    private Integer purchaseId;
    private CustomerDTO customer;
    private List<ProductInShopDTO> products;
    private BigDecimal totalPrice;
    private boolean shopping = true;

    public PurchaseDTO() {
    }

    public PurchaseDTO(Integer purchaseId, CustomerDTO customer, List<ProductInShopDTO> products, BigDecimal totalPrice, boolean shopping) {
        this.purchaseId = purchaseId;
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
        this.shopping = shopping;
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

    public List<ProductInShopDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInShopDTO> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    @Override
    public String toString() {
        return "PurchaseTo{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                ", shopping=" + shopping +
                '}';
    }
}

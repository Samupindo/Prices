package com.develop.prices.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseTo implements Serializable {

    private Integer purchaseId;
    private CustomerTo customer;
    private List<ProductInShopTo> products;
    private BigDecimal totalPrice;
    private boolean shopping = true;

    public PurchaseTo() {
    }

    public PurchaseTo(Integer purchaseId, CustomerTo customer, List<ProductInShopTo> products, BigDecimal totalPrice, boolean shopping) {
        this.purchaseId = purchaseId;
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
        this.shopping = shopping;
    }

    public PurchaseTo(Integer purchaseId, CustomerTo customer, BigDecimal totalPrice) {
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

    public CustomerTo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerTo customer) {
        this.customer = customer;
    }

    public List<ProductInShopTo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInShopTo> products) {
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

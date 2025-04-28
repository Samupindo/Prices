package com.develop.prices.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="purchases")
public class PurchaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private List<ShopProductInfoModel> products = new ArrayList<>();

    @Transient
    private BigDecimal totalPrice;

    public PurchaseModel() {
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public List<ShopProductInfoModel> getProducts() {
        return products;
    }

    public void setProducts(List<ShopProductInfoModel> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        this.totalPrice = products.stream()
                .map(ShopProductInfoModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "PurchaseModel{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
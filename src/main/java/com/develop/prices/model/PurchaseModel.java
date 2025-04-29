package com.develop.prices.model;


import jakarta.persistence.*;

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

    @OneToMany(
            mappedBy = "purchase",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<PurchaseProductModel> purchaseProductModels = new ArrayList<>();

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

    public List<PurchaseProductModel> getPurchaseProductModels() {
        return purchaseProductModels;
    }

    public void setPurchaseProductModels(List<PurchaseProductModel> purchaseProductModels) {
        this.purchaseProductModels = purchaseProductModels;
    }

    public BigDecimal getTotalPrice() {
        if (purchaseProductModels == null) {
            return BigDecimal.ZERO;
        }
        return purchaseProductModels.stream()
                .map(item -> item.getProductInShop().getPrice())
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", purchaseProductModels=" + purchaseProductModels +
                '}';
    }
}
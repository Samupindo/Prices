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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "purchase_product_info",
            joinColumns = @JoinColumn(name= "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_product_info_id")
    )
    private List<ProductInShopModel> products = new ArrayList<>();

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

    public List<ProductInShopModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInShopModel> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        this.totalPrice = products.stream()
                .map(ProductInShopModel::getPrice)
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
package com.develop.prices.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="purchases")
public class PurchaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductPriceModel> info;
    private BigDecimal totalPrice;

    public PurchaseModel() {
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

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", info=" + info +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
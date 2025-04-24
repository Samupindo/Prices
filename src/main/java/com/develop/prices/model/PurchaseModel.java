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
    private Set<ProductPriceModel> productPriceModel;

    //TODO Holi
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

    public Set<ProductPriceModel> getProductPriceModel() {
        return productPriceModel;
    }

    public void setProductPriceModel(Set<ProductPriceModel> productPriceModel) {
        this.productPriceModel = productPriceModel;
    }

    public BigDecimal getTotalPrice() {
        this.totalPrice = productPriceModel.stream()
                .map(ProductPriceModel::getPrice)
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
                ", products=" + productPriceModel +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
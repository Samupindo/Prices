package com.develop.prices.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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

    @OneToMany(
            mappedBy = "purchase",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<PurchaseLineModel> purchaseLineModels = new ArrayList<>();

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

    public List<PurchaseLineModel> getPurchaseLineModels() {
        return purchaseLineModels;
    }

    public void setPurchaseLineModels(List<PurchaseLineModel> purchaseLineModels) {
        this.purchaseLineModels = purchaseLineModels;
    }

    public BigDecimal getTotalPrice() {
        if (purchaseLineModels == null) {
            return BigDecimal.ZERO;
        }
        return purchaseLineModels.stream()
                .map(item -> item.getProductInShop().getPrice())
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "purchaseId=" + purchaseId +
                ", customer=" + customer +
                ", purchaseLineModels=" + purchaseLineModels +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
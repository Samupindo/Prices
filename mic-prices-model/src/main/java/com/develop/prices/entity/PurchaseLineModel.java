package com.develop.prices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import java.io.Serializable;

@Entity
@Table(name = "purchase_line")
public class PurchaseLineModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_line_id")
    private Integer purchaseLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseModel purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_in_shop_id", nullable = false)
    private ProductInShopModel productInShop;

    public PurchaseLineModel() {
    }

    // Constructor útil
    public PurchaseLineModel(PurchaseModel purchase, ProductInShopModel productInShop) {
        this.purchase = purchase;
        this.productInShop = productInShop;
    }

    public Integer getPurchaseLineId() {
        return purchaseLineId;
    }

    public void setPurchaseLineId(Integer purchaseLineId) {
        this.purchaseLineId = purchaseLineId;
    }

    public PurchaseModel getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseModel purchase) {
        this.purchase = purchase;
    }

    public ProductInShopModel getProductInShop() {
        return productInShop;
    }

    public void setProductInShop(ProductInShopModel productInShop) {
        this.productInShop = productInShop;
    }

    @Override
    public String toString() {
        return "PurchaseLineModel{" +
                "purchaseLineId=" + purchaseLineId +
                ", purchase=" + purchase +
                ", productInShop=" + productInShop +
                '}';
    }
}
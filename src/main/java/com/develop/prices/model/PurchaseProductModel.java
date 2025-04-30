package com.develop.prices.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "purchase_product")
public class PurchaseProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_product_id")
    private Integer purchaseProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseModel purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_in_shop_id", nullable = false)
    private ProductInShopModel productInShop;

    public PurchaseProductModel() {}

    // Constructor útil
    public PurchaseProductModel(PurchaseModel purchase, ProductInShopModel productInShop) {
        this.purchase = purchase;
        this.productInShop = productInShop;
    }

    public Integer getPurchaseProductId() {
        return purchaseProductId;
    }

    public void setPurchaseProductId(Integer purchaseProductId) {
        this.purchaseProductId = purchaseProductId;
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
        return "PurchaseProductModel{" +
                "purchaseProductId=" + purchaseProductId +
                ", purchase=" + purchase +
                ", productInShop=" + productInShop +
                '}';
    }
}
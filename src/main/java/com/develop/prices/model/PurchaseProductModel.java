package com.develop.prices.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "purchase_product") // Mapea a la tabla del ERD
public class PurchaseProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_product_id") // Mapea a la columna PK del ERD
    private Integer purchaseProductId; // Nombre de campo Java

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false) // FK a purchases
    private PurchaseModel purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_in_shop_id", nullable = false) // FK a product_in_shop
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
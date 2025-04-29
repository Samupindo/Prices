package com.develop.prices.model;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product_in_shop")
public class ProductInShopModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productInShopId;

    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopModel shop;

    @ManyToMany(mappedBy = "products" , fetch = FetchType.LAZY)
    private List<PurchaseModel> purchases = new ArrayList<>();

    public ProductInShopModel() {
    }

    public ProductInShopModel(BigDecimal price, ProductModel product, ShopModel shop) {
        this.price = price;
        this.product = product;
        this.shop = shop;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public ShopModel getShop() {
        return shop;
    }

    public void setShop(ShopModel shop) {
        this.shop = shop;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getProductInShopId() {
        return productInShopId;
    }

    public void setProductInShopId(Integer productInShopId) {
        this.productInShopId = productInShopId;
    }

    public List<PurchaseModel> getPurchase() {
        return purchases;
    }

    public void setPurchase(List<PurchaseModel> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "ProductInShopModel{" +
                "productInShopId=" + productInShopId +
                ", price=" + price +
                ", product=" + product +
                ", shop=" + shop +
                ", purchase=" + purchases +
                '}';
    }
}


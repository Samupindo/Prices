package com.develop.prices.model;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product_price")
public class ProductPriceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productPriceId;

    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopModel shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseModel purchase;

    public ProductPriceModel() {
    }
// si
    public ProductPriceModel(BigDecimal price, ProductModel product, ShopModel shop) {
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

    public Integer getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(Integer productPriceId) {
        this.productPriceId = productPriceId;
    }

    public PurchaseModel getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseModel purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "ProductPriceModel{" +
                "productPriceId=" + productPriceId +
                ", price=" + price +
                ", product=" + product +
                ", shop=" + shop +
                ", purchase=" + purchase +
                '}';
    }
}


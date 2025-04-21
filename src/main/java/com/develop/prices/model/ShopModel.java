package com.develop.prices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
@Table(name = "shops")
public class ShopModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ⚠️ ESTA es la clave
    private Integer shopId;

    private String country;
    private String city;
    private String address;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPriceModel> prices;

    // Constructor vacío y con parámetros
    public ShopModel() {
    }

    public ShopModel(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    // Getters y Setters
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

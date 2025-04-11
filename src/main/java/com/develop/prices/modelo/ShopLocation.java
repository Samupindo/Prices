package com.develop.prices.modelo;

import jakarta.persistence.*;


@Entity
@Table(name = "shop_locations")
public class ShopLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ⚠️ ESTA es la clave
    private Integer shopId;

    private String country;
    private String city;
    private String address;

    // Constructor vacío y con parámetros
    public ShopLocation() {
    }

    public ShopLocation(String country, String city, String address) {
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

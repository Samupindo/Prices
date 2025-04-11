package com.example.projectProducts.modelo;

public class ShopLocation {
    private static Integer contador= 1;
    private Integer shopId;
    private String country;
    private String city;
    private String address;

    public ShopLocation() {
    }

    public ShopLocation(String country, String city, String address) {
        this.shopId = contador++;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public static Integer getNextId(){
        return contador++;
    }

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

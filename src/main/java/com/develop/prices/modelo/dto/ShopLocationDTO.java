package com.develop.prices.modelo.dto;


public class ShopLocationDTO {

    private static Integer contador= 1;
    private Integer shopId;
    private String country;
    private String city;
    private String address;

    public ShopLocationDTO() {
    }

    public ShopLocationDTO(String country, String city, String address) {
        this.shopId = contador++;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public ShopLocationDTO(Integer shopId, String country, String city, String address) {
        this.shopId = shopId;
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

package com.develop.prices.dto;


public class ShopDTO {


    private Integer shopId;
    private String country;
    private String city;
    private String address;

    public ShopDTO() {
    }

    public ShopDTO(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public ShopDTO(Integer shopId, String country, String city, String address) {
        this.shopId = shopId;
        this.country = country;
        this.city = city;
        this.address = address;
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

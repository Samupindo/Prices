package com.develop.prices.dto;

import com.develop.prices.validation.StringNotBlank;

import java.io.Serializable;

public class ShopPutDTO implements Serializable {
    @StringNotBlank
    private String country;
    @StringNotBlank
    private String city;
    @StringNotBlank
    private String address;

    public ShopPutDTO() {
    }

    public ShopPutDTO(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
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

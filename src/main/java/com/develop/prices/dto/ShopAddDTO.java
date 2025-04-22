package com.develop.prices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShopAddDTO {

    @Size(min = 3, max = 100)
    @NotBlank
    private String country;

    @Size(min = 3,max = 100)
    @NotBlank
    private String city;

    @Size(max = 100)
    @NotBlank
    private String address;

    public ShopAddDTO() {
    }

    public ShopAddDTO(String country, String city, String address) {
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

    @Override
    public String toString() {
        return "ShopAddDTO{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}

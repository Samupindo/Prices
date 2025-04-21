package com.develop.prices.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ShopAddDTO {

    @NotBlank(message = "El campo country no debe ir vacío")
    private String country;
    @NotBlank(message = "El campo city no debe ir vacío")
    private String city;
    @NotBlank(message = "El campo address no debe ir vacío")
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

package com.develop.prices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShopAddDTO {

    @Size(min = 3, max = 100, message = "The name can only hace 100 characters")
    @NotBlank(message = "The field country cannot be empty")
    private String country;

    @Size(min = 3,max = 100, message = "The name can only hace 100 characters")
    @NotBlank(message = "The field city cannot be empty")
    private String city;

    @Size(max = 100, message = "The name can only hace 100 characters")
    @NotBlank(message = "The field address cannot be empty")
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

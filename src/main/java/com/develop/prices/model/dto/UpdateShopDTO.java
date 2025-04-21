package com.develop.prices.model.dto;

import jakarta.validation.constraints.NotBlank;

// import jakarta.validation.constraints.NotBlank;
//@NotBlank
//@JsonIgnoreProperties(ignoreUnknown = false)
public class UpdateShopDTO {
    @NotBlank(message = "El campo country no debe ir vacío")
    private String country;
    @NotBlank(message = "El campo city no debe ir vacío")
    private String city;
    @NotBlank(message = "El campo address no debe ir vacío")
    private String address;

    public UpdateShopDTO() {
    }

    public UpdateShopDTO(String country, String city, String address) {
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

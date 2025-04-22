package com.develop.prices.dto;

import jakarta.validation.constraints.NotBlank;

//@JsonIgnoreProperties(ignoreUnknown = false)
public class UpdateShopDTO {
    @NotBlank(message = "The field country cannot be empty")
    private String country;
    @NotBlank(message = "The field city cannot be empty")
    private String city;
    @NotBlank(message = "The field address cannot be empty")
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

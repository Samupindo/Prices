package com.example.projectProducts.modelo.dto;

import java.io.Serializable;

public class LocationDTO implements Serializable {

    private String country;
    private String city;
    private String address;

    public LocationDTO() {
    }

    public LocationDTO(String country, String adress, String city) {
        this.country = country;
        this.address = adress;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

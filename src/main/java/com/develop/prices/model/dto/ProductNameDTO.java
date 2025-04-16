package com.develop.prices.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductNameDTO {
//    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "El nombre debe contener solo letras y espacios")

    private String name;

    public ProductNameDTO(){

    }
    public ProductNameDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

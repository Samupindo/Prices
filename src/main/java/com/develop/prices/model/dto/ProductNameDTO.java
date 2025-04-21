package com.develop.prices.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductNameDTO {

    @NotBlank(message = "The field name cannot be empty")
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

package com.example.projectProducts.modelo.dto;

public class ProductNameDTO {
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

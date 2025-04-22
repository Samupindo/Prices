package com.develop.prices.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductNameDTO {

    @Size(max = 100, message = "The name can only have 100 characteres")
    @Pattern(regexp = "\\p{L}[\\p{L}\\s]+", message = "The name can only have letters and spaces")
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

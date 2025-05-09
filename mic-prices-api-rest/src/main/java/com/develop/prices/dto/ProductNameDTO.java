package com.develop.prices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class ProductNameDTO implements Serializable {

    @Size(max = 100, message = "The name can only have 100 characteres")
    @Pattern(regexp = "\\p{L}[\\p{L}\\s]+", message = "The name can only have letters and spaces")
    @NotBlank
    private String name;

    public ProductNameDTO() {

    }

    public ProductNameDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

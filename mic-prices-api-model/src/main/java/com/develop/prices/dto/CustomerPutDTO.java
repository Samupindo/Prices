package com.develop.prices.dto;

import com.develop.prices.validation.PatchPutNotBlank;
import com.develop.prices.validation.PatchPutNotNull;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerPutDTO {

    @Size(max = 100, message = "The name can only have 100 characteres")
    @Pattern(regexp = "\\p{L}[\\p{L}\\s]+", message = "The name can only have letters and spaces")
    @PatchPutNotBlank
    private String name;

    @PatchPutNotNull
    private Integer phone;

    @PatchPutNotBlank
    private String email;

    public CustomerPutDTO() {
    }
    public CustomerPutDTO(String name, Integer phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

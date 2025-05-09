package com.develop.prices.dto;

import com.develop.prices.validation.StringNotBlank;
import com.develop.prices.validation.PhoneNotNull;

import java.io.Serializable;

public class CreateCustomerDTO implements Serializable {

    @StringNotBlank
    private String name;
    @PhoneNotNull
    private Integer phone;
    @StringNotBlank
    private String email;

    public CreateCustomerDTO() {
    }

    public CreateCustomerDTO(String name, Integer phone, String email) {
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

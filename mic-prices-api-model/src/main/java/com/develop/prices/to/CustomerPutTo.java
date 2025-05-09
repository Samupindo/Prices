package com.develop.prices.to;


import java.io.Serializable;

public class CustomerPutTo implements Serializable {

    private String name;

    private Integer phone;

    private String email;

    public CustomerPutTo() {
    }
    public CustomerPutTo(String name, Integer phone, String email) {
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

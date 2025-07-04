package com.develop.prices.to;

import java.io.Serializable;

public class ShopTo implements Serializable {

  private Integer shopId;
  private String country;
  private String city;
  private String address;

  public ShopTo() {}

  public ShopTo(String country, String city, String address) {
    this.country = country;
    this.city = city;
    this.address = address;
  }

  public ShopTo(Integer shopId, String country, String city, String address) {
    this.shopId = shopId;
    this.country = country;
    this.city = city;
    this.address = address;
  }

  public Integer getShopId() {
    return shopId;
  }

  public void setShopId(Integer shopId) {
    this.shopId = shopId;
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

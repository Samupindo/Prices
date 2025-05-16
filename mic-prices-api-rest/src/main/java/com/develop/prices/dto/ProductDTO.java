package com.develop.prices.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
  private Integer productId;
  private String name;

  public ProductDTO() {}

  public ProductDTO(Integer productId, String name) {
    this.productId = productId;
    this.name = name;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ProductModel{" + "productId=" + productId + ", name='" + name + '\'' + '}';
  }
}

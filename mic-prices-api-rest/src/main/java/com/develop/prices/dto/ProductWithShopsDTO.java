package com.develop.prices.dto;

import java.io.Serializable;
import java.util.List;

public class ProductWithShopsDTO implements Serializable {
  private Integer productId;
  private String name;
  private List<ShopInfoDTO> shop;

  public ProductWithShopsDTO() {}

  public ProductWithShopsDTO(Integer productId, String name, List<ShopInfoDTO> shop) {
    this.productId = productId;
    this.name = name;
    this.shop = shop;
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

  public List<ShopInfoDTO> getShop() {
    return shop;
  }

  public void setShop(List<ShopInfoDTO> shop) {
    this.shop = shop;
  }

  @Override
  public String toString() {
    return "ProductWithShopsDTO{"
        + "productId="
        + productId
        + ", name='"
        + name
        + '\''
        + ", shop="
        + shop
        + '}';
  }
}

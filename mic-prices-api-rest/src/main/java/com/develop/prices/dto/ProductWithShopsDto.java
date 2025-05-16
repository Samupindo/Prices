package com.develop.prices.dto;

import java.io.Serializable;
import java.util.List;

public class ProductWithShopsDto implements Serializable {
  private Integer productId;
  private String name;
  private List<ShopInfoDto> shop;

  public ProductWithShopsDto() {}

  public ProductWithShopsDto(Integer productId, String name, List<ShopInfoDto> shop) {
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

  public List<ShopInfoDto> getShop() {
    return shop;
  }

  public void setShop(List<ShopInfoDto> shop) {
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

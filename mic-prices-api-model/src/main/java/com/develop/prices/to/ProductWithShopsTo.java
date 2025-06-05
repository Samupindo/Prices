package com.develop.prices.to;

import java.io.Serializable;
import java.util.List;

public class ProductWithShopsTo implements Serializable {
  private Integer productId;
  private String name;
  private List<ShopInfoTo> shop;

  public ProductWithShopsTo() {}

  public ProductWithShopsTo(Integer productId, String name, List<ShopInfoTo> shop) {
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

  public List<ShopInfoTo> getShop() {
    return shop;
  }

  public void setShop(List<ShopInfoTo> shop) {
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

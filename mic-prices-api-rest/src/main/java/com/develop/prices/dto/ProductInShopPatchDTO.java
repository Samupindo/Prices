package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInShopPatchDTO implements Serializable {

  private BigDecimal price;

  public ProductInShopPatchDTO() {}

  public ProductInShopPatchDTO(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "ProductInShopModel{" + ", price=" + price + '}';
  }
}

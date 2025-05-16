package com.develop.prices.to;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddProductShopTo implements Serializable {

  private BigDecimal price;

  public AddProductShopTo() {}

  public AddProductShopTo(BigDecimal price) {
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

package com.develop.prices.to;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopInfoTo implements Serializable {
  private Integer productInShopId;
  private Integer shopId;
  private BigDecimal price;

  public ShopInfoTo() {}

  public ShopInfoTo(Integer productInShopId, Integer shopId, BigDecimal price) {
    this.productInShopId = productInShopId;
    this.shopId = shopId;
    this.price = price;
  }

  public ShopInfoTo(Integer shopId, BigDecimal price) {
    this.shopId = shopId;
    this.price = price;
  }

  public Integer getShopId() {
    return shopId;
  }

  public void setShopId(Integer shopId) {
    this.shopId = shopId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getProductInShopId() {
    return productInShopId;
  }

  public void setProductInShopId(Integer productInShopId) {
    this.productInShopId = productInShopId;
  }

  @Override
  public String toString() {
    return "ShopInfoTo{" + "shopId=" + shopId + ", price=" + price + '}';
  }
}

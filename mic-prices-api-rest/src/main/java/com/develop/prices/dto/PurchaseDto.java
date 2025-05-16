package com.develop.prices.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseDto implements Serializable {

  private Integer purchaseId;
  private CustomerDto customer;
  private List<ProductInShopDto> products;
  private BigDecimal totalPrice;
  private boolean shopping = true;

  public PurchaseDto() {}

  public PurchaseDto(
      Integer purchaseId,
      CustomerDto customer,
      List<ProductInShopDto> products,
      BigDecimal totalPrice,
      boolean shopping) {
    this.purchaseId = purchaseId;
    this.customer = customer;
    this.products = products;
    this.totalPrice = totalPrice;
    this.shopping = shopping;
  }

  public PurchaseDto(Integer purchaseId, CustomerDto customer, BigDecimal totalPrice) {
    this.purchaseId = purchaseId;
    this.customer = customer;
    this.totalPrice = totalPrice;
  }

  public Integer getPurchaseId() {
    return purchaseId;
  }

  public void setPurchaseId(Integer purchaseId) {
    this.purchaseId = purchaseId;
  }

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public List<ProductInShopDto> getProducts() {
    return products;
  }

  public void setProducts(List<ProductInShopDto> products) {
    this.products = products;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public boolean isShopping() {
    return shopping;
  }

  public void setShopping(boolean shopping) {
    this.shopping = shopping;
  }

  @Override
  public String toString() {
    return "PurchaseTo{"
        + "purchaseId="
        + purchaseId
        + ", customer="
        + customer
        + ", products="
        + products
        + ", totalPrice="
        + totalPrice
        + ", shopping="
        + shopping
        + '}';
  }
}

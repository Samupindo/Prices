package com.develop.prices.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer productId;

  private String name;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<ProductInShopModel> prices;

  public ProductModel() {}

  public ProductModel(String name) {
    this.name = name;
  }

  public List<ProductInShopModel> getPrices() {
    return prices;
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

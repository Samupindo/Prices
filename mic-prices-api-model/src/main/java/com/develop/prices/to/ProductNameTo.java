package com.develop.prices.to;

import java.io.Serializable;

public class ProductNameTo implements Serializable {

  private String name;

  public ProductNameTo() {}

  public ProductNameTo(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

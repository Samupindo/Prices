package com.develop.prices.to;

import java.io.Serializable;

public class PostPurchaseTo implements Serializable {
  private Integer customerId;

  public PostPurchaseTo() {}

  public PostPurchaseTo(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }
}

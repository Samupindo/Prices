package com.develop.prices.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class PostPurchaseDto implements Serializable {
  @NotNull private Integer customerId;

  public PostPurchaseDto() {}

  public PostPurchaseDto(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }
}

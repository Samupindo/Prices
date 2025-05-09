package com.develop.prices.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


public class PostPurchaseDTO implements Serializable {
    @NotNull
    private Integer customerId;

    public PostPurchaseDTO() {
    }

    public PostPurchaseDTO(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}

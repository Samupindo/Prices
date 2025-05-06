package com.develop.prices.to;

import jakarta.validation.constraints.NotNull;


public class PostPurchaseTo {
    @NotNull
    private Integer customerId;

    public PostPurchaseTo() {
    }

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

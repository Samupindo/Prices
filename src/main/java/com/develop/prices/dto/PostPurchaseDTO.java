package com.develop.prices.dto;

import jakarta.validation.constraints.NotNull;


public class PostPurchaseDTO {
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

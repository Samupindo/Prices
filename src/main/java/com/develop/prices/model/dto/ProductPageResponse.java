package com.develop.prices.model.dto;

import java.util.List;

public class ProductPageResponse {
    private List<ProductWithShopsDTO> content;
    private long totalElements;
    private int totalPages;

    // Constructor
    public ProductPageResponse(List<ProductWithShopsDTO> content, long totalElements, int totalPages, boolean empty) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ProductWithShopsDTO> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setContent(List<ProductWithShopsDTO> content) {
        this.content = content;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}


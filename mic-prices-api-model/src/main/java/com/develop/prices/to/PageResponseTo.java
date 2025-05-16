package com.develop.prices.to;

import java.util.List;

public class PageResponseTo<T> {
  private List<T> content;
  private long totalElements;
  private int totalPages;

  public PageResponseTo(List<T> content, long totalElements, int totalPages) {
    this.content = content;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  public List<T> getContent() {
    return content;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}


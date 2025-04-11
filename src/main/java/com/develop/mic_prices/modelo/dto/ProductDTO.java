package com.example.projectProducts.modelo.dto;

public class ProductDTO {
    private static Integer  contador = 1;
    private Integer productId;
    private String name;

    public ProductDTO() {
    }

    public ProductDTO(String name) {
        this.productId = contador++;
        this.name = name;
    }

    public static Integer getNextId(){
        return contador++;
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
        return "ProductModel{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                '}';
    }
}

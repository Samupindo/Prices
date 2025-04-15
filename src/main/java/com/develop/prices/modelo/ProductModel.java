package com.develop.prices.modelo;

import jakarta.persistence.*;

import  java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // deja que la base de datos lo autogenere
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPriceModel> prices;

    public ProductModel() {
    }

    public ProductModel(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return id;
    }

    public void setProductId(Integer id) {
        this.id = id;
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
                "productId=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

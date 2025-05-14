package com.develop.prices.specification;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class ProductInShopSpecification {

    public static Specification<ProductModel> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<ProductModel> hasPriceMin(BigDecimal priceMin) {
        return (root, query, criteriaBuilder) -> {
            Join<ProductModel, ProductInShopModel> join = root.join("prices", JoinType.LEFT);
            return criteriaBuilder.greaterThanOrEqualTo(join.get("price"), priceMin);
        };
    }

    public static Specification<ProductModel> hasPriceMax(BigDecimal priceMax) {
        return (root, query, criteriaBuilder) -> {
            Join<ProductModel, ProductInShopModel> join = root.join("prices", JoinType.LEFT);
            return criteriaBuilder.lessThanOrEqualTo(join.get("price"), priceMax);
        };
    }

}


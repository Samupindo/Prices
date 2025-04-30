package com.develop.prices.specification;

import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ProductInShopModel;
import com.develop.prices.model.PurchaseLineModel;
import com.develop.prices.model.PurchaseModel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseSpecification {

    public static Specification<PurchaseModel> hasCustomer(Integer customerId) {
        return (root, query, criteriaBuilder) -> {
            Join<CustomerModel, PurchaseModel> join = root.join("customer", JoinType.LEFT);
            return criteriaBuilder.equal(join.get("customerId"), customerId);
        };
    }

    public static Specification<PurchaseModel> hasProductInShop(List<Integer> productInShop) {
        return (root, query, cb) -> {
            Join<PurchaseModel, PurchaseLineModel> purchaseProductJoin = root.join("purchaseProductModels", JoinType.INNER);
            Join<PurchaseLineModel, ProductInShopModel> productInShopJoin = purchaseProductJoin.join("productInShop", JoinType.INNER);
            return productInShopJoin.get("productInShopId").in(productInShop);
        };
    }

    public static Specification<PurchaseModel> hasPriceMax(BigDecimal priceMax) {
        return (root, query, criteriaBuilder) -> {
            // Crear subquery
            Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
            Root<PurchaseLineModel> subRoot = subquery.from(PurchaseLineModel.class);
            Join<PurchaseLineModel, ProductInShopModel> pricesJoin = subRoot.join("info");

            subquery.select(criteriaBuilder.sum(pricesJoin.get("price")))
                    .where(criteriaBuilder.equal(subRoot.get("purchase"), root));

            return criteriaBuilder.lessThanOrEqualTo(subquery, priceMax);
        };
    }


    public static Specification<PurchaseModel> hasPriceMin(BigDecimal priceMin) {
        return (root, query, criteriaBuilder) -> {
            // Crear subquery
            Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
            Root<PurchaseLineModel> subRoot = subquery.from(PurchaseLineModel.class);
            Join<PurchaseLineModel, ProductInShopModel> pricesJoin = subRoot.join("info");

            subquery.select(criteriaBuilder.sum(pricesJoin.get("price")))
                    .where(criteriaBuilder.equal(subRoot.get("purchase"), root));

            return criteriaBuilder.greaterThanOrEqualTo(subquery, priceMin);
        };

    }


}

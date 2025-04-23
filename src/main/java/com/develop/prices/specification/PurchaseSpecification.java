package com.develop.prices.specification;

import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.model.CustomerModel;
import com.develop.prices.model.ProductModel;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.PurchaseModel;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Set;

public class PurchaseSpecification {

    public static Specification<PurchaseModel> hasPurchaseId(Integer purchaseId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("purchaseId"), purchaseId);
        };
    }
    public static Specification<PurchaseModel> hasCustomer(Integer customerId) {
        return (root, query, criteriaBuilder) -> {
            Join<CustomerModel, PurchaseModel> join = root.join("customer", JoinType.LEFT);
            return criteriaBuilder.equal(join.get("customerId"), customerId);
        };
    }

    public static Specification<PurchaseModel> hasProductPrice(Set<ProductPriceModel> info) {
        return (root, query, cb) -> {
            Join<PurchaseModel, ProductPriceModel> join = root.join("info");
            return join.get("productPriceId").in(info);
        };
    }

    public static Specification<PurchaseModel> hasPriceMax(BigDecimal priceMax) {
        return (root, query, criteriaBuilder) -> {
            // Crear subquery
            Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
            Root<PurchaseModel> subRoot = subquery.from(PurchaseModel.class);
            Join<PurchaseModel, ProductPriceModel> pricesJoin = subRoot.join("info");

            subquery.select(criteriaBuilder.sum(pricesJoin.get("price")))
                    .where(criteriaBuilder.equal(root.get("purchaseId"), subRoot.get("purchaseId")));

            return criteriaBuilder.lessThanOrEqualTo(subquery, priceMax);
        };
    }


    public static Specification<PurchaseModel> hasPriceMin(BigDecimal priceMin) {
        return (root, query, criteriaBuilder) -> {
            // Crear subquery
            Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
            Root<PurchaseModel> subRoot = subquery.from(PurchaseModel.class);
            Join<PurchaseModel, ProductPriceModel> pricesJoin = subRoot.join("info");

            subquery.select(criteriaBuilder.sum(pricesJoin.get("price")))
                    .where(criteriaBuilder.equal(root.get("purchaseId"), subRoot.get("purchaseId")));

            return criteriaBuilder.greaterThanOrEqualTo(subquery, priceMin);
        };

    }


}

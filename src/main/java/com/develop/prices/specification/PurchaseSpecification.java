package com.develop.prices.specification;

import com.develop.prices.dto.CustomerDTO;
import com.develop.prices.model.ProductPriceModel;
import com.develop.prices.model.PurchaseModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class PurchaseSpecification {

    public static Specification<PurchaseModel> hasPurchaseId(Integer purchaseId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("purchaseId"), purchaseId);
        };
    }
    public static Specification<PurchaseModel> hasCustomer(CustomerDTO customer) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("customer"), customer);
        };
    }

    public static Specification<PurchaseModel> hasProductPrice(Set<ProductPriceModel> info) {
        return (root, query, cb) -> {
            Join<PurchaseModel, ProductPriceModel> join = root.join("info");
            return join.get("productPriceId").in(info);
        };
    }



}

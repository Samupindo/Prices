package com.develop.prices.specification;

import com.develop.prices.entity.CustomerModel;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

  public static Specification<CustomerModel> hasCustomerId(Integer customerId) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("customerId"), customerId);
    };
  }

  public static Specification<CustomerModel> hasName(String name) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    };
  }

  public static Specification<CustomerModel> hasEmail(String email) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    };
  }

  public static Specification<CustomerModel> hasPhone(Integer phone) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("phone"), phone);
    };
  }
}

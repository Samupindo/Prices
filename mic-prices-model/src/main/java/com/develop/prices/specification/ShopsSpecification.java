package com.develop.prices.specification;

import com.develop.prices.entity.ShopModel;
import org.springframework.data.jpa.domain.Specification;

public class ShopsSpecification {

  public static Specification<ShopModel> findByCountry(String country) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get("country")), "%" + country.toLowerCase() + "%");
    };
  }

  public static Specification<ShopModel> findByCity(String city) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    };
  }

  public static Specification<ShopModel> findByAddress(String address) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    };
  }

  public static Specification<ShopModel> findByShopId(Integer shopId) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("shopId"), shopId);
    };
  }
}

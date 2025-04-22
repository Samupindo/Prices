package com.develop.prices.repository;

import com.develop.prices.model.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseRepository extends JpaRepository<PurchaseModel,Integer>, JpaSpecificationExecutor {
}

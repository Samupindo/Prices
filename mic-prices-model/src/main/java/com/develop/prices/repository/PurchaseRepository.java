package com.develop.prices.repository;

import com.develop.prices.entity.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PurchaseRepository extends JpaRepository<PurchaseModel,Integer>, JpaSpecificationExecutor<PurchaseModel> {
}

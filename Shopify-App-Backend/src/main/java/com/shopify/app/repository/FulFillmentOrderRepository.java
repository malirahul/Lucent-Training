package com.shopify.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.app.entities.orderEntities.FulfillmentEntity;

@Repository
public interface FulFillmentOrderRepository extends JpaRepository<FulfillmentEntity, Long> {

}

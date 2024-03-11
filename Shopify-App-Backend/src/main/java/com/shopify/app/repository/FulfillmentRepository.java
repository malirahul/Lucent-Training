package com.shopify.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopify.app.entities.orderEntities.Fulfillments;

public interface FulfillmentRepository extends JpaRepository<Fulfillments, Long>{

}

package com.shopify.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.app.entities.productEntities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{




}

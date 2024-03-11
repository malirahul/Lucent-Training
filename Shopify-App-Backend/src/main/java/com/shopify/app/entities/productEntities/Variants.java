package com.shopify.app.entities.productEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Variants {

	@Id
	private Long id;
	private Long product_id;
    private String title;
    private String price;
    private String sku;
    private Integer position;
    private String inventoryPolicy;
    private String compare_at_price;
    private String fulfillmentService;
    private String inventoryManagement;
    private String option1;
    private String option2;
    private String option3;
    private String createdAt;
    private String updatedAt;
    private Boolean taxable;
    private double weight;
    private String weightUnit;
//    private Long inventoryItemId;
//    private Integer inventoryQuantity;
//    private Integer oldInventoryQuantity;
//    private Boolean requiresShipping;
    private String adminGraphqlApiId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getInventoryPolicy() {
		return inventoryPolicy;
	}
	public void setInventoryPolicy(String inventoryPolicy) {
		this.inventoryPolicy = inventoryPolicy;
	}
	public String getCompare_at_price() {
		return compare_at_price;
	}
	public void setCompare_at_price(String compare_at_price) {
		this.compare_at_price = compare_at_price;
	}
	public String getFulfillmentService() {
		return fulfillmentService;
	}
	public void setFulfillmentService(String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}
	public String getInventoryManagement() {
		return inventoryManagement;
	}
	public void setInventoryManagement(String inventoryManagement) {
		this.inventoryManagement = inventoryManagement;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Boolean getTaxable() {
		return taxable;
	}
	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getAdminGraphqlApiId() {
		return adminGraphqlApiId;
	}
	public void setAdminGraphqlApiId(String adminGraphqlApiId) {
		this.adminGraphqlApiId = adminGraphqlApiId;
	}


}

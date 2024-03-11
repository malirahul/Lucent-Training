package com.shopify.app.entities.orderEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lineItemsOrders")
public class LineItem {
	@Id
	private Long id;
    private Long variant_id;
    private String title;
    private Long quantity;
    private String sku;
    private String variant_title;
    private String vendor;
    private String fulfillment_service;
    private Long product_id;
    private Boolean requires_shipping;
    private Boolean taxable;
    private Boolean gift_card;
    private String name;
    private Boolean product_exists;
    private Long fulfillable_quantity;
    private Long grams;
    private String price;
    private String total_discount;
    private String fulfillment_status;
    private String admin_graphql_api_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVariant_id() {
		return variant_id;
	}
	public void setVariant_id(Long variant_id) {
		this.variant_id = variant_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getVariant_title() {
		return variant_title;
	}
	public void setVariant_title(String variant_title) {
		this.variant_title = variant_title;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getFulfillment_service() {
		return fulfillment_service;
	}
	public void setFulfillment_service(String fulfillment_service) {
		this.fulfillment_service = fulfillment_service;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public Boolean getRequires_shipping() {
		return requires_shipping;
	}
	public void setRequires_shipping(Boolean requires_shipping) {
		this.requires_shipping = requires_shipping;
	}
	public Boolean getTaxable() {
		return taxable;
	}
	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}
	public Boolean getGift_card() {
		return gift_card;
	}
	public void setGift_card(Boolean gift_card) {
		this.gift_card = gift_card;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getProduct_exists() {
		return product_exists;
	}
	public void setProduct_exists(Boolean product_exists) {
		this.product_exists = product_exists;
	}
	public Long getFulfillable_quantity() {
		return fulfillable_quantity;
	}
	public void setFulfillable_quantity(Long fulfillable_quantity) {
		this.fulfillable_quantity = fulfillable_quantity;
	}
	public Long getGrams() {
		return grams;
	}
	public void setGrams(Long grams) {
		this.grams = grams;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(String total_discount) {
		this.total_discount = total_discount;
	}
	public String getFulfillment_status() {
		return fulfillment_status;
	}
	public void setFulfillment_status(String fulfillment_status) {
		this.fulfillment_status = fulfillment_status;
	}
	public String getAdmin_graphql_api_id() {
		return admin_graphql_api_id;
	}
	public void setAdmin_graphql_api_id(String admin_graphql_api_id) {
		this.admin_graphql_api_id = admin_graphql_api_id;
	}


}

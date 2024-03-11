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
@Table(name = "lineItems")
public class LineItemsEntity {

	@Id
	private Long id;
	private Long fulfillment_order_id;
	private Long quantity;
	private Long line_item_id;
	private Long inventory_item_id;
	private Long fulfillable_quantity;
	private Long variant_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFulfillment_order_id() {
		return fulfillment_order_id;
	}
	public void setFulfillment_order_id(Long fulfillment_order_id) {
		this.fulfillment_order_id = fulfillment_order_id;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getLine_item_id() {
		return line_item_id;
	}
	public void setLine_item_id(Long line_item_id) {
		this.line_item_id = line_item_id;
	}
	public Long getInventory_item_id() {
		return inventory_item_id;
	}
	public void setInventory_item_id(Long inventory_item_id) {
		this.inventory_item_id = inventory_item_id;
	}
	public Long getFulfillable_quantity() {
		return fulfillable_quantity;
	}
	public void setFulfillable_quantity(Long fulfillable_quantity) {
		this.fulfillable_quantity = fulfillable_quantity;
	}
	public Long getVariant_id() {
		return variant_id;
	}
	public void setVariant_id(Long variant_id) {
		this.variant_id = variant_id;
	}


}

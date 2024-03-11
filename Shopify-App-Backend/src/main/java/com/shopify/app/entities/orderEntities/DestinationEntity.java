package com.shopify.app.entities.orderEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
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
@Table(name = "destination")
@Embeddable
public class DestinationEntity {

	@Id
	private long id;


    @JsonProperty("fulfillment_order_id")
    private Long fulfillmentOrderId;

    private Long quantity;

    @JsonProperty("line_item_id")
    private Long lineItemId;

    @JsonProperty("inventory_item_id")
    private Long inventoryItemId;

    @JsonProperty("fulfillable_quantity")
    private Long fulfillableQuantity;

    @JsonProperty("variant_id")
    private Long variantId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getFulfillmentOrderId() {
		return fulfillmentOrderId;
	}

	public void setFulfillmentOrderId(Long fulfillmentOrderId) {
		this.fulfillmentOrderId = fulfillmentOrderId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Long getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public Long getFulfillableQuantity() {
		return fulfillableQuantity;
	}

	public void setFulfillableQuantity(Long fulfillableQuantity) {
		this.fulfillableQuantity = fulfillableQuantity;
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}



}

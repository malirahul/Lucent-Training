package com.shopify.app.dto.fulfillmentOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class DestinationDTO {

	@Id
	private long id;

    @JsonProperty("shop_id")
    private long shopId;

    @JsonProperty("fulfillment_order_id")
    private long fulfillmentOrderId;

    private int quantity;

    @JsonProperty("line_item_id")
    private long lineItemId;

    @JsonProperty("inventory_item_id")
    private long inventoryItemId;

    @JsonProperty("fulfillable_quantity")
    private int fulfillableQuantity;

    @JsonProperty("variant_id")
    private long variantId;
    // getters and setters
}


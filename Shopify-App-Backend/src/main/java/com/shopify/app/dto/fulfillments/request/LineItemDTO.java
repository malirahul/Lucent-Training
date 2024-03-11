package com.shopify.app.dto.fulfillments.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LineItemDTO {
	@JsonProperty("fulfillment_order_id")
	private Long fulfillmentOrderId;

	@JsonProperty("fulfillment_order_line_items")
    private List<FulfillmentOrderLineItemDTO> fulfillmentOrderLineItems;

}

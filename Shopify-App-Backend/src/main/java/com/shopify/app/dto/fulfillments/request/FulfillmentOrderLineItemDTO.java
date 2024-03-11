package com.shopify.app.dto.fulfillments.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FulfillmentOrderLineItemDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("quantity")
    private Long quantity;
}

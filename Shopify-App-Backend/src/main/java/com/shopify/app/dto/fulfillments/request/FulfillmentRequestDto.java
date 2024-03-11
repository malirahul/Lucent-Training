package com.shopify.app.dto.fulfillments.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FulfillmentRequestDto {
	@JsonProperty("fulfillment")
	private FulfillmentDto fulfillment;

	public FulfillmentDto getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(FulfillmentDto fulfillment) {
		this.fulfillment = fulfillment;
	}


}

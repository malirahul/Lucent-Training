package com.shopify.app.dto.fulfillments.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FulfillmentDto {
	@JsonProperty("message")
	private String message;

	@JsonProperty("notify_customer")
    private boolean notifyCustomer;

	@JsonProperty("tracking_info")
	private TrackingInfoDTO trackingInfo;


	@JsonProperty("line_items_by_fulfillment_order")
    private List<LineItemDTO> lineItemsByFulfillmentOrder;


}

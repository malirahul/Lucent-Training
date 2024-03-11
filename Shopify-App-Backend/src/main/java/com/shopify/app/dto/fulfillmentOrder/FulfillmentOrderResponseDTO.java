package com.shopify.app.dto.fulfillmentOrder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FulfillmentOrderResponseDTO {

    @JsonProperty("fulfillment_orders")
    private List<FulfillmentOrderDTO> fulfillmentOrders;

	public List<FulfillmentOrderDTO> getFulfillmentOrders() {
		return fulfillmentOrders;
	}

	public void setFulfillmentOrders(List<FulfillmentOrderDTO> fulfillmentOrders) {
		this.fulfillmentOrders = fulfillmentOrders;
	}


}


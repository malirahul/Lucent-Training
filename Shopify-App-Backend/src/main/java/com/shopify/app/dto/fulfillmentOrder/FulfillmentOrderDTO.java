package com.shopify.app.dto.fulfillmentOrder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FulfillmentOrderDTO {

	private long id;

    @JsonProperty("order_id")
    private long orderId;

    @JsonProperty("assigned_location_id")
    private long assignedLocationId;

    @JsonProperty("request_status")
    private String requestStatus;

    private String status;


    private DestinationDTO destination;

    @JsonProperty("line_items")
	private List<LineItemDTO> lineItems;


    @JsonProperty("fulfill_at")
    private String fulfillAt;

    @JsonProperty("fulfill_by")
    private String fulfillBy;



    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
package com.shopify.app.dto.fulfillments.response;

import java.util.List;

public class FulfillmentDTO {

	private Long id;
	private Long order_id;
	private String status;
	private String created_at;
	private String service;
	private String updated_at;
	private String tracking_company;
	private Long location_id;
	private String origin_address;
	private List<LineItemDto> line_items;
	private String tracking_number;
	private List<String> tracking_numbers;
	private String tracking_url;
	private String name;
	private String admin_graphql_api_id;

}

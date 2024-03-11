package com.shopify.app.dto.fulfillmentOrder;

import lombok.Data;

@Data
public class LineItemDTO {

	private Long id;
	private Long shop_id;
	private Long fulfillment_order_id;
	private Long quantity;
	private Long line_item_id;
	private Long inventory_item_id;
	private Long fulfillable_quantity;
	private Long variant_id;
}


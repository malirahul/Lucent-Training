package com.shopify.app.dto.orderModel;

import lombok.Data;

@Data
public class OrderRequestDto {

	private OrderDto order;

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
	}



}

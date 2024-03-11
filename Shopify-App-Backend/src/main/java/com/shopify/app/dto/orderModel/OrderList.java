package com.shopify.app.dto.orderModel;

import java.util.List;

import lombok.Data;

@Data
public class OrderList {
	private List<OrderDto> orders;

	public List<OrderDto> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}


}

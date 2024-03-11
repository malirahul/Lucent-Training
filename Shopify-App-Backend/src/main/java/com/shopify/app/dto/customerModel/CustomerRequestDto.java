package com.shopify.app.dto.customerModel;

import lombok.Data;

@Data
public class CustomerRequestDto {

	private CustomerDto  customer;

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

}

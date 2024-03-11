package com.shopify.app.dto.customerModel;

import java.util.List;

import lombok.Data;

@Data
public class CustomerList {
	private List<CustomerDto> customers;

	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}


}

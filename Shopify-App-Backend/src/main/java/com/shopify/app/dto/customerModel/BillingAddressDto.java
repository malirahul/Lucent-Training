package com.shopify.app.dto.customerModel;

import lombok.Data;

@Data
public class BillingAddressDto {

	private String first_name;
	private String address1;
	private Long phone;
	private String city;
	private String zip;
	private String country;
	private String last_name;
	private String company;
	private String name;
	private String country_code;

}

package com.shopify.app.dto;

import lombok.Data;

@Data
public class Login{
	private String shopname;

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

}

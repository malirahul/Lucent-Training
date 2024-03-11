package com.shopify.app.dto.orderModel;

import com.shopify.app.dto.fulfillments.response.PresentmentMoneyDTO;
import com.shopify.app.dto.fulfillments.response.ShopMoneyDTO;

import lombok.Data;

@Data
public class PriceSetDTO {
	private ShopMoneyDTO shop_money;
    private PresentmentMoneyDTO presentment_money;
	public ShopMoneyDTO getShop_money() {
		return shop_money;
	}
	public void setShop_money(ShopMoneyDTO shop_money) {
		this.shop_money = shop_money;
	}
	public PresentmentMoneyDTO getPresentment_money() {
		return presentment_money;
	}
	public void setPresentment_money(PresentmentMoneyDTO presentment_money) {
		this.presentment_money = presentment_money;
	}



}

package com.shopify.app.dto.fulfillments.response;

import com.shopify.app.dto.orderModel.PriceSetDTO;

import lombok.Data;

@Data
public class TaxLineDTO {

	private String title;
    private String price;
    private Double rate;
    private String channel_liable;
    private PriceSetDTO price_set;

}

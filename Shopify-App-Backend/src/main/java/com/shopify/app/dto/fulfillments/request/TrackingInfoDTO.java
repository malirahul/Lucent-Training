package com.shopify.app.dto.fulfillments.request;

import lombok.Data;

@Data
public class TrackingInfoDTO {
	private Long number;
    private String url;
    private String company;
}

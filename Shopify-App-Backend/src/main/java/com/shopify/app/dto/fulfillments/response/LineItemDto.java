package com.shopify.app.dto.fulfillments.response;

import java.util.List;

public class LineItemDto {

	private Long id;
    private Long variant_id;
    private String title;
    private Long quantity;
    private String sku;
    private String variant_title;
    private String vendor;
    private String fulfillment_service;
    private Long product_id;
    private Boolean requires_shipping;
    private Boolean taxable;
    private Boolean gift_card;
    private String name;
    private Boolean product_exists;
    private Long fulfillable_quantity;
    private Long grams;
    private String price;
    private String total_discount;
    private String fulfillment_status;
    private String admin_graphql_api_id;
    private List<TaxLineDTO> tax_lines;
}

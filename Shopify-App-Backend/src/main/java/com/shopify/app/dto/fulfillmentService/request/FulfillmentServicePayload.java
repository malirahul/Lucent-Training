package com.shopify.app.dto.fulfillmentService.request;

import lombok.Data;

@Data
public class FulfillmentServicePayload {

	    private String name;
	    private String callbackUrl;
	    private boolean inventoryManagement;
	    private boolean trackingSupport;
	    private boolean requiresShippingMethod;
	    private String format;
	    private boolean permitsSkuSharing;
	    private boolean fulfillmentOrdersOptIn;


}

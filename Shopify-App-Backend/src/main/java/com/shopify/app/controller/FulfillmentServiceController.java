package com.shopify.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopify.app.dto.fulfillmentService.FulfillmentServiceDto;
import com.shopify.app.repository.FulFillmentOrderRepository;
import com.shopify.app.repository.UserRepository;
import com.shopify.app.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/fulfillment-service")
public class FulfillmentServiceController {

	Logger log = LoggerFactory.getLogger(FulfillmentServiceController.class);


	public final WebClient client = WebClient.create("https://lucenttrainingstore.myshopify.com/admin/api/2023-01");

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FulFillmentOrderRepository fillmentOrderRepository;

	@Autowired
	private JwtUtils jwtService;

	@PostMapping("/callback_url")
	public void handleFulfillmentRequest(@RequestBody FulfillmentServiceDto fulfillmentServiceDto,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepository.findByShopName(shopName).get().getAccessToken();

		// Handle fulfillment service request
		// ...
		log.info("FulfillmentService: " + fulfillmentServiceDto);
	}

	@PostMapping("/fulfillment_order_notification")
	public ResponseEntity<Void> handleFulfillmentOrderNotification(@RequestBody Map<String, Object> payload,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepository.findByShopName(shopName).get().getAccessToken();

		String kind = (String) payload.get("kind");

		// Check the kind property to determine the type of notification
//	    if ("fulfillment_request".equals(kind)) {
//	        handleFulfillmentRequest(payload, accessToken);
//	    } else if ("cancellation_request".equals(kind)) {
//	        handleFulfillmentCancellation(payload, accessToken);
//	    }

		return ResponseEntity.ok().build();
	}

	private void handleFulfillmentCancellation(Map<String, Object> payload, String accessToken) {
		// TODO Auto-generated method stub

	}

//	private void handleFulfillmentRequest(Map<String, Object> payload, String accessToken) {
//	    // Extract necessary data from the payload
//	    String orderId = (String) payload.get("id");
//	    String fulfillmentStatus = (String) payload.get("fulfillment_status");
//
//	    // Handle fulfillment request
//	    if ("fulfilled".equals(fulfillmentStatus)) {
//	        // Process fulfillment request
//	        processFulfillmentRequest(orderId, accessToken);
//	    } else if (fulfillmentStatus == null || "null".equals(fulfillmentStatus)) {
//	        // Process fulfillment cancellation
//	        processFulfillmentCancellation(orderId, accessToken);
//	    }
//	}

//	private void processFulfillmentRequest(String orderId, String accessToken) {
//	    try {
//	        // Retrieve the fulfillment request details using the orderId and accessToken
//	        FulfillmentRequest fulfillmentRequest = retrieveFulfillmentRequest(orderId, accessToken);
//
//	        // Process the fulfillment request
//	        // Example implementation:
//	        if (fulfillmentRequest != null) {
//	            // Extract relevant information from the fulfillment request
//	            String productId = fulfillmentRequest.getProductId();
//	            int quantity = fulfillmentRequest.getQuantity();
//	            String shippingAddress = fulfillmentRequest.getShippingAddress();
//
//	            // Perform actions based on the fulfillment request
//	            // Example actions:
//	            if (isProductAvailable(productId, quantity)) {
//	                // Fulfill the order by updating inventory, generating shipping label, etc.
//	                fulfillOrder(orderId, productId, quantity, shippingAddress);
//
//	                // Update the fulfillment status in the database or external system
//	                updateFulfillmentStatus(orderId, "fulfilled");
//
//	                // Send a confirmation email to the customer
//	                sendConfirmationEmail(orderId, fulfillmentRequest.getCustomerEmail());
//
//	                // Log the fulfillment request processing
//	                log.info("Fulfillment request processed successfully for order ID: " + orderId);
//	            } else {
//	                // Handle case when the product is out of stock or unavailable
//	                // Mark the order as backordered or take appropriate action
//	                handleProductUnavailable(orderId);
//
//	                // Log the fulfillment request processing
//	                log.info("Product is unavailable for fulfillment request with order ID: " + orderId);
//	            }
//	        } else {
//	            // Handle case when the fulfillment request is not found or invalid
//	            // Log an error or take appropriate action
//	            log.error("Invalid fulfillment request with order ID: " + orderId);
//	        }
//	    } catch (Exception e) {
//	        // Handle any exceptions that occur during fulfillment request handling
//	        log.error("Error handling fulfillment request for order ID: " + orderId + ". Message: " + e.getMessage());
//	    }

//	private FulfillmentRequest retrieveFulfillmentRequest(String orderId, String accessToken) {
//	    // Implement the logic to retrieve the fulfillment request details
//	    // using the orderId and accessToken
//	    // Return the FulfillmentRequest object or null if not found
//	    // ...
//	}
//
//	private boolean isProductAvailable(String productId, int quantity) {
//	    // Implement the logic to check if the product with the given ID is available
//	    // and has sufficient quantity to fulfill the request
//	    // Return true if available, false otherwise
//	    // ...
//	}

	private void fulfillOrder(String orderId, String productId, int quantity, String shippingAddress) {
		// Implement the logic to fulfill the order by updating inventory,
		// generating shipping labels, etc.
		// ...
	}

	private void updateFulfillmentStatus(String orderId, String status) {
		// Implement the logic to update the fulfillment status
		// of the order with the given ID in the database or external system
		// ...
	}

	private void sendConfirmationEmail(String orderId, String customerEmail) {
		// Implement the logic to send a confirmation email to the customer
		// regarding the fulfillment of their order
		// ...
	}

	private void handleProductUnavailable(String orderId) {
		// Implement the logic to handle cases when the product is unavailable
		// or out of stock for fulfillment
		// Mark the order as backordered or take appropriate action
		// ...
	}

	private void processFulfillmentCancellation(String orderId, String accessToken) {
		// Implement the logic to handle fulfillment cancellation
		// ...
		log.info("Handling fulfillment cancellation for order ID: " + orderId);
	}

}

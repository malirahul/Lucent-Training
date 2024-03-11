package com.shopify.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopify.app.dto.fulfillmentOrder.FulfillmentOrderDTO;
import com.shopify.app.dto.fulfillmentOrder.FulfillmentOrderResponseDTO;
import com.shopify.app.dto.fulfillments.request.FulfillmentRequestDto;
import com.shopify.app.dto.fulfillments.response.FulfillmentResponseDto;
import com.shopify.app.dto.orderModel.OrderDto;
import com.shopify.app.dto.orderModel.OrderList;
import com.shopify.app.dto.orderModel.OrderRequestDto;
import com.shopify.app.entities.orderEntities.FulfillmentEntity;
import com.shopify.app.entities.orderEntities.OrderEntity;
import com.shopify.app.repository.FulFillmentOrderRepository;
import com.shopify.app.repository.OrderRepository;
import com.shopify.app.repository.UserRepository;
import com.shopify.app.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@Slf4j
public class OrderController {

	Logger log = LoggerFactory.getLogger(OrderController.class);


	@Autowired
	private OrderRepository repository;

	public final WebClient client = WebClient.create("https://lucenttrainingstore.myshopify.com/admin/api/2023-01");

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FulFillmentOrderRepository fillmentOrderRepository;

	@Autowired
	private JwtUtils jwtService;

	@CrossOrigin(origins = "http://localhost:3001")
	@GetMapping("/orders")
	public List<OrderDto> fetchAllOrders(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Inside fetch all orders");
		Mono<OrderList> response = client.get().uri("/orders.json").header("X-Shopify-Access-Token", accessToken)
				.header("Content-Type", "application/json").retrieve().bodyToMono(OrderList.class);

		OrderList orderDtoList = response.block();
		log.info("Order List: " + orderDtoList);

		assert orderDtoList != null;
		List<OrderDto> oDtoList = orderDtoList.getOrders();
		List<OrderEntity> orderList = new ArrayList<>();

		oDtoList.forEach(ord -> {
			OrderEntity order = new OrderEntity();
			BeanUtils.copyProperties(ord, order);

			orderList.add(order);
		});
		repository.saveAll(orderList);

		return orderDtoList.getOrders();
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/createOrder")
	public OrderDto createOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Order : " + orderDto);
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setOrder(orderDto);
		log.info("request: " + orderRequestDto);

		try {

			Mono<OrderRequestDto> response = client.post().uri("/orders.json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.body(Mono.just(orderRequestDto), OrderRequestDto.class).retrieve()
					.bodyToMono(OrderRequestDto.class);

			OrderDto createdOrder = response.block().getOrder();
			OrderEntity orderEntity = new OrderEntity();
			log.info("Order entity" + orderEntity);
			BeanUtils.copyProperties(createdOrder, orderEntity);
			OrderEntity savedOrder = repository.save(orderEntity);
			log.info("Order Saved Sucessfully: " + savedOrder);
			return createdOrder;
		} catch (Exception e) {
			log.error("Exception occured in OrderController : createOrder() with msg => " + e.getLocalizedMessage());
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@DeleteMapping("/deleteOrders/{id}")
	public void deleteOrder(@PathVariable("id") Long orderId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("id " + orderId);
		try {
			// Delete Order from Shopify store
			client.delete().uri("/orders/" + orderId + ".json").header("X-Shopify-Access-Token", accessToken).retrieve()
					.bodyToMono(Void.class).block();

			// Delete product from database
			repository.deleteById(orderId);
			log.info("Order with id: " + orderId + " deleted successfully.");
		} catch (Exception e) {
			log.error("Exception occurred in orderController : deleteOrder() with msg => " + e.getLocalizedMessage());
		}
	}

	@GetMapping("/orders/{id}/fulfillment_orders")
	public List<FulfillmentOrderDTO> fetchFulfillmentOrder(@PathVariable("id") Long orderId,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Inside fullment Request");
		Mono<FulfillmentOrderResponseDTO> response = client.get().uri("/orders/" + orderId + "/fulfillment_orders.json")
				.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json").retrieve()
				.bodyToMono(FulfillmentOrderResponseDTO.class);

		FulfillmentOrderResponseDTO fulfillmentRequestDto = response.block();

		assert fulfillmentRequestDto != null;
		List<FulfillmentOrderDTO> fulfillOrderDtoList = fulfillmentRequestDto.getFulfillmentOrders();

		System.out.println("Dto : " + fulfillOrderDtoList);
//		List<FulfillmentEntity> fulfillmentEntityList = new ArrayList<>();

		FulfillmentEntity fulfill = new FulfillmentEntity();
		BeanUtils.copyProperties(fulfillOrderDtoList.get(0), fulfill);
		System.out.println("Entity : " + fulfill);

//			fulfillmentEntityList.add(fulfill);
//		});
		fillmentOrderRepository.save(fulfill);

		return fulfillmentRequestDto.getFulfillmentOrders();
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/fulfillments")
	public FulfillmentResponseDto createFulfillments(@RequestBody FulfillmentRequestDto fulfillment,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("FulFillment : " + fulfillment);

		FulfillmentRequestDto fulfillmentRequestDto = new FulfillmentRequestDto();
		fulfillmentRequestDto.setFulfillment(fulfillment.getFulfillment());
		log.info("request: " + fulfillmentRequestDto);

		try {

			Mono<FulfillmentResponseDto> response = client.post().uri("/fulfillments.json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.bodyValue(fulfillmentRequestDto).retrieve().bodyToMono(FulfillmentResponseDto.class);

			// log.info("Response : {}", response.block());
			FulfillmentResponseDto createdFulfillment = response.block();
			return createdFulfillment;

		} catch (Exception e) {
			log.error("Exception occured in OrderFulfillmentController : createdFulfillment() with msg => "
					+ e.getLocalizedMessage());
		}
		return null;
	}

}

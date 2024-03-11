package com.shopify.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopify.app.dto.customerModel.CustomerDto;
import com.shopify.app.dto.customerModel.CustomerList;
import com.shopify.app.dto.customerModel.CustomerRequestDto;
import com.shopify.app.entities.customerEntities.Customer;
import com.shopify.app.repository.CustomerRepository;
import com.shopify.app.repository.UserRepository;
import com.shopify.app.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@Slf4j
public class CustomerController {

	Logger log = LoggerFactory.getLogger(CustomerController.class);


	@Autowired
	private CustomerRepository repository;

	public final WebClient client = WebClient.create("https://lucenttrainingstore.myshopify.com/admin/api/2023-01");

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtUtils jwtService;

	@CrossOrigin(origins = "http://localhost:3001")
	@GetMapping("/customers")
	@Cacheable(value = "customers")
	public List<CustomerDto> fetchAllCustomers(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Inside fetch all customers");
		Mono<CustomerList> response = client.get().uri("/customers.json").header("X-Shopify-Access-Token", accessToken)
				.header("Content-Type", "application/json").retrieve().bodyToMono(CustomerList.class);

		CustomerList customerDtoList = response.block();
		log.info("Customer List: " + customerDtoList);

		assert customerDtoList != null;
		List<CustomerDto> custDtoList = customerDtoList.getCustomers();
		List<Customer> customerList = new ArrayList<>();

		custDtoList.forEach(cust -> {
			Customer customer = new Customer();
			BeanUtils.copyProperties(cust, customer);

			customerList.add(customer);
		});
		repository.saveAll(customerList);

		return customerDtoList.getCustomers();
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@GetMapping("/customers/{id}")
	@Cacheable(key = "#id", value = "customers")
	public CustomerDto fetchCustomer(@PathVariable("id") Long customerId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		CustomerRequestDto customerRequestDto = new CustomerRequestDto();

		try {

			Mono<CustomerRequestDto> response = client.get().uri("/customers/" + customerId + ".json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json").retrieve()
					.bodyToMono(CustomerRequestDto.class);

			CustomerDto getCustomer = response.block().getCustomer();
			Customer customerEntity = new Customer();
			log.info("Customer get Sucessfully: " + getCustomer);
			return getCustomer;
		} catch (Exception e) {
			log.error("Exception occured in CustomerController : getCustomer() with msg => " + e.getLocalizedMessage());
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/createCustomer")
	public CustomerDto createCustomer(@RequestBody CustomerDto customerDto, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();


		log.info("Customer : " + customerDto);
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomer(customerDto);
		log.info("request: " + customerRequestDto);

		try {

			Mono<CustomerRequestDto> response = client.post().uri("/customers.json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.body(Mono.just(customerRequestDto), CustomerRequestDto.class).retrieve()
					.bodyToMono(CustomerRequestDto.class);

			CustomerDto createdCustomer = response.block().getCustomer();
			Customer customerEntity = new Customer();
			log.info("customer entity" + customerEntity);
			BeanUtils.copyProperties(createdCustomer, customerEntity);
			Customer savedCustomer = repository.save(customerEntity);
			log.info("Customer Saved Sucessfully: " + savedCustomer);
			return createdCustomer;
		} catch (Exception e) {
			log.error("Exception occured in CustomerController : createCustomer() with msg => "
					+ e.getLocalizedMessage());
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PutMapping("/updateCustomer/{id}")
	@CachePut(key = "#id", value = "updateCustomer")
	public CustomerDto updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomerDto customerDto,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Customer : " + customerDto);
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomer(customerDto);
		log.info("request: " + customerRequestDto);

		try {

			Mono<CustomerRequestDto> response = client.put().uri("/customers/" + customerId + ".json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.body(Mono.just(customerRequestDto), CustomerRequestDto.class).retrieve()
					.bodyToMono(CustomerRequestDto.class);

			CustomerDto updatedCustomer = response.block().getCustomer();
			Customer customerEntity = new Customer();
			log.info("customer entity" + customerEntity);
			BeanUtils.copyProperties(updatedCustomer, customerEntity);
			Customer updateCustomer = repository.save(customerEntity);
			log.info("Customer Update Sucessfully: " + updateCustomer);
			return updatedCustomer;
		} catch (Exception e) {
			log.error("Exception occured in CustomerController : updateCustomer() with msg => "
					+ e.getLocalizedMessage());
		}
		return null;
	}

	@DeleteMapping("/deleteCustomer/{id}")
	@CacheEvict(key = "#id", value = "deleteCustomer")
	public void deleteCustomer(@PathVariable("id") Long customerId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();
		log.info("id " + customerId);
		try {
			// Delete customer from Shopify store
			client.delete().uri("/customers/" + customerId + ".json").header("X-Shopify-Access-Token", accessToken)
					.retrieve().bodyToMono(Void.class).block();

			// Delete customer from database
			repository.deleteById(customerId);
			log.info("Customer with id: " + customerId + " deleted successfully.");
		} catch (Exception e) {
			log.error("Exception occurred in CustomerController : deleteCustomer() with msg => "
					+ e.getLocalizedMessage());
		}
	}

}
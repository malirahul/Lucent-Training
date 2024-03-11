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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopify.app.dto.productModel.ProductDto;
import com.shopify.app.dto.productModel.ProductList;
import com.shopify.app.dto.productModel.ProductRequestDto;
import com.shopify.app.entities.productEntities.Product;
import com.shopify.app.repository.ProductRepository;
import com.shopify.app.repository.UserRepository;
import com.shopify.app.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@Slf4j
public class ProductController {

	Logger log = LoggerFactory.getLogger(ProductController.class);

	public final WebClient client = WebClient.create("https://lucenttrainingstore.myshopify.com/admin/api/2023-01");

	@Autowired
	private ProductRepository repository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtUtils jwtService;

	@CrossOrigin(origins = "http://localhost:3001")
	@GetMapping("/products")
	public List<ProductDto> fetchAllProducts(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Inside fetch all products");
		Mono<ProductList> response = client.get().uri("/products.json").header("X-Shopify-Access-Token", accessToken)
				.header("Content-Type", "application/json").retrieve().bodyToMono(ProductList.class);

		ProductList productDtoList = response.block();
		log.info("Product List: " + productDtoList);

		assert productDtoList != null;
		List<ProductDto> prodDtoList = productDtoList.getProducts();
		List<Product> productList = new ArrayList<>();

		prodDtoList.forEach(prod -> {
			Product product = new Product();
			BeanUtils.copyProperties(prod, product);
			repository.save(product);
			productList.add(product);
		});

		return productDtoList.getProducts();
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@GetMapping("/products/{id}")
	public ProductDto fetchCustomer(@PathVariable("id") Long productId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		ProductRequestDto productRequestDto = new ProductRequestDto();

		try {

			Mono<ProductRequestDto> response = client.get().uri("/products/" + productId + ".json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json").retrieve()
					.bodyToMono(ProductRequestDto.class);

			ProductDto getProduct = response.block().getProduct();
			Product productEntity = new Product();
			log.info("Product get Sucessfully: " + getProduct);
			return getProduct;
		} catch (Exception e) {
			log.error("Exception occured in ProductController : getProduct() with msg => " + e.getLocalizedMessage());
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/createProduct")
	public ProductDto createProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Product : " + productDto);
		ProductRequestDto productRequestDto = new ProductRequestDto();
		productRequestDto.setProduct(productDto);
		log.info("request: " + productRequestDto);

		try {

			Mono<ProductRequestDto> response = client.post().uri("/products.json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.body(Mono.just(productRequestDto), ProductRequestDto.class).retrieve()
					.bodyToMono(ProductRequestDto.class);

			ProductDto createdProduct = response.block().getProduct();
			Product productEntity = new Product();
			log.info("product entity" + productEntity);
			BeanUtils.copyProperties(createdProduct, productEntity);
			Product savedProduct = repository.save(productEntity);
			log.info("Product Saved Sucessfully: " + savedProduct);
			return createdProduct;
		} catch (Exception e) {
			log.error(
					"Exception occured in ProductController : createProduct() with msg => " + e.getLocalizedMessage());
		}
		return null;
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@DeleteMapping("/deleteProduct/{id}")
	public void deleteProduct(@PathVariable("id") Long productId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("id " + productId);
		try {
			// Delete product from Shopify store
			client.delete().uri("/products/" + productId + ".json").header("X-Shopify-Access-Token", accessToken)
					.retrieve().bodyToMono(Void.class).block();

			// Delete product from database
			repository.deleteById(productId);
			log.info("Product with id: " + productId + " deleted successfully.");
		} catch (Exception e) {
			log.error(
					"Exception occurred in ProductController : deleteProduct() with msg => " + e.getLocalizedMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:3001")
	@PutMapping("/updateProduct/{id}")
	public ProductDto updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		String shopName = jwtService.getUserNameFromJwtToken(token);
		String accessToken = userRepo.findByShopName(shopName).get().getAccessToken();

		log.info("Product : " + productDto);
		ProductRequestDto productRequestDto = new ProductRequestDto();
		productRequestDto.setProduct(productDto);
		log.info("request: " + productRequestDto);

		try {

			Mono<ProductRequestDto> response = client.put().uri("/products/" + productId + ".json")
					.header("X-Shopify-Access-Token", accessToken).header("Content-Type", "application/json")
					.body(Mono.just(productRequestDto), ProductRequestDto.class).retrieve()
					.bodyToMono(ProductRequestDto.class);

			ProductDto updatedProduct = response.block().getProduct();
			Product productEntity = new Product();
			log.info("Product entity" + productEntity);
			BeanUtils.copyProperties(updatedProduct, productEntity);
			Product updateProduct = repository.save(productEntity);
			log.info("Product Update Sucessfully: " + updateProduct);
			return updatedProduct;
		} catch (Exception e) {
			log.error(
					"Exception occured in ProductController : updateProduct() with msg => " + e.getLocalizedMessage());
		}
		return null;
	}

}

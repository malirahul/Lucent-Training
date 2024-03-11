package com.shopify.app.dto.productModel;

import lombok.Data;

@Data
public class ProductRequestDto {

	private ProductDto product;

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

}

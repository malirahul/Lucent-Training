package com.shopify.app.dto.productModel;

import java.util.List;

import lombok.Data;

@Data
public class ProductList {
    private List<ProductDto> products;

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}


}

package com.shopify.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RestResponse<T> {

	private boolean isError;
	private int statusCode;
	private ErrorDto error;
	private T data;
}

package com.shopify.app.entities.orderEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fulfillments")
public class Fulfillments {

	@Id
	private Long id;
	private Long order_id;
	private String status;
	private String created_at;
	private String service;
	private String updated_at;
	private String tracking_company;
	private Long location_id;
	private String origin_address;


	private String tracking_number;


	private String tracking_url;
	private String name;
	private String admin_graphql_api_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getTracking_company() {
		return tracking_company;
	}
	public void setTracking_company(String tracking_company) {
		this.tracking_company = tracking_company;
	}
	public Long getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}
	public String getOrigin_address() {
		return origin_address;
	}
	public void setOrigin_address(String origin_address) {
		this.origin_address = origin_address;
	}
	public String getTracking_number() {
		return tracking_number;
	}
	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}
	public String getTracking_url() {
		return tracking_url;
	}
	public void setTracking_url(String tracking_url) {
		this.tracking_url = tracking_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdmin_graphql_api_id() {
		return admin_graphql_api_id;
	}
	public void setAdmin_graphql_api_id(String admin_graphql_api_id) {
		this.admin_graphql_api_id = admin_graphql_api_id;
	}



}

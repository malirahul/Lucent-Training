package com.shopify.app.dto.customerModel;

import java.util.List;

import com.shopify.app.entities.customerEntities.Addresses;

import lombok.Data;

@Data
public class CustomerDto {

	private Long id;
	private String email;
    private String created_at;
    private String updated_at;
    private String first_name;
    private String last_name;
    private Integer orders_count;
    private String state;
    private String total_spent;
    private Boolean verified_email;
    private String last_order_name;
    private String currency;
    private String phone;
    private List<Addresses> addresses;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Integer getOrders_count() {
		return orders_count;
	}
	public void setOrders_count(Integer orders_count) {
		this.orders_count = orders_count;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTotal_spent() {
		return total_spent;
	}
	public void setTotal_spent(String total_spent) {
		this.total_spent = total_spent;
	}
	public Boolean getVerified_email() {
		return verified_email;
	}
	public void setVerified_email(Boolean verified_email) {
		this.verified_email = verified_email;
	}
	public String getLast_order_name() {
		return last_order_name;
	}
	public void setLast_order_name(String last_order_name) {
		this.last_order_name = last_order_name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Addresses> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Addresses> addresses) {
		this.addresses = addresses;
	}


}

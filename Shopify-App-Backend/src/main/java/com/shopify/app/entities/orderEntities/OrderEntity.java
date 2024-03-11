package com.shopify.app.entities.orderEntities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	private Long id;
	private String admin_graphql_api_id;

    private Long app_id;
    private Long checkout_id;
    private String checkout_token;
    private String closed_at;
    private String company;
    private boolean confirmed;
	private String contact_email;

	private String created_at;
	private String currency;
	private String current_total_price;
    private String email;
    private String financial_status;
    private String fulfillment_status;
    private String name;
    private Long order_number;
    private String order_status_url;
    private String phone;
    private String processed_at;
    private String source_name;
    private String tags;
    private String token;
    private String updated_at;
    private String user_id;

    //private List<BillingAddress> billing_address;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer", referencedColumnName = "id")
//    private Customer customer;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fulfillment",referencedColumnName = "id")
//	private FulfillmentEntity fulfillment;
//
    @OneToMany(cascade = CascadeType.ALL)
	private List<LineItem> line_items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdmin_graphql_api_id() {
		return admin_graphql_api_id;
	}

	public void setAdmin_graphql_api_id(String admin_graphql_api_id) {
		this.admin_graphql_api_id = admin_graphql_api_id;
	}

	public Long getApp_id() {
		return app_id;
	}

	public void setApp_id(Long app_id) {
		this.app_id = app_id;
	}

	public Long getCheckout_id() {
		return checkout_id;
	}

	public void setCheckout_id(Long checkout_id) {
		this.checkout_id = checkout_id;
	}

	public String getCheckout_token() {
		return checkout_token;
	}

	public void setCheckout_token(String checkout_token) {
		this.checkout_token = checkout_token;
	}

	public String getClosed_at() {
		return closed_at;
	}

	public void setClosed_at(String closed_at) {
		this.closed_at = closed_at;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrent_total_price() {
		return current_total_price;
	}

	public void setCurrent_total_price(String current_total_price) {
		this.current_total_price = current_total_price;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFinancial_status() {
		return financial_status;
	}

	public void setFinancial_status(String financial_status) {
		this.financial_status = financial_status;
	}

	public String getFulfillment_status() {
		return fulfillment_status;
	}

	public void setFulfillment_status(String fulfillment_status) {
		this.fulfillment_status = fulfillment_status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrder_number() {
		return order_number;
	}

	public void setOrder_number(Long order_number) {
		this.order_number = order_number;
	}

	public String getOrder_status_url() {
		return order_status_url;
	}

	public void setOrder_status_url(String order_status_url) {
		this.order_status_url = order_status_url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProcessed_at() {
		return processed_at;
	}

	public void setProcessed_at(String processed_at) {
		this.processed_at = processed_at;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public List<LineItem> getLine_items() {
		return line_items;
	}

	public void setLine_items(List<LineItem> line_items) {
		this.line_items = line_items;
	}


}

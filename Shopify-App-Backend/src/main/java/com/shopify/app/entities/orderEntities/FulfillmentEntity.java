package com.shopify.app.entities.orderEntities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
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
@Table(name = "fulfillment_order")
public class FulfillmentEntity {

	@Id
	private Long id;


	@JsonProperty("order_id")
	private Long orderId;

	@JsonProperty("assigned_location_id")
	private Long assignedLocationId;

	@JsonProperty("request_status")
	private String requestStatus;

	private String status;


	@Embedded
	private DestinationEntity destination;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonProperty("line_items")
	private List<LineItemsEntity> lineItems;

	@JsonProperty("fulfill_at")
	private String fulfillAt;

	@JsonProperty("fulfill_by")
	private String fulfillBy;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getAssignedLocationId() {
		return assignedLocationId;
	}

	public void setAssignedLocationId(Long assignedLocationId) {
		this.assignedLocationId = assignedLocationId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DestinationEntity getDestination() {
		return destination;
	}

	public void setDestination(DestinationEntity destination) {
		this.destination = destination;
	}

	public List<LineItemsEntity> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItemsEntity> lineItems) {
		this.lineItems = lineItems;
	}

	public String getFulfillAt() {
		return fulfillAt;
	}

	public void setFulfillAt(String fulfillAt) {
		this.fulfillAt = fulfillAt;
	}

	public String getFulfillBy() {
		return fulfillBy;
	}

	public void setFulfillBy(String fulfillBy) {
		this.fulfillBy = fulfillBy;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}


}


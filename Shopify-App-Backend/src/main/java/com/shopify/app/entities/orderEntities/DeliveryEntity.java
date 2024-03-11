package com.shopify.app.entities.orderEntities;

import jakarta.persistence.Embeddable;
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
@Table(name = "table_delivery")
@Embeddable
public class DeliveryEntity {

	@Id
	private long id;
	private String method_type;
//	private String min_delivery_date_time;
//	private String max_delivery_date_time;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMethod_type() {
		return method_type;
	}
	public void setMethod_type(String method_type) {
		this.method_type = method_type;
	}


}

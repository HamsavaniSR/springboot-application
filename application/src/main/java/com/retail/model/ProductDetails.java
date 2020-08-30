package com.retail.model;

import java.io.Serializable;

//import javax.persistence.Entity;
//import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
//@Table(name="ProductDetails")
@Document(collection="ProductDetails")
public class ProductDetails implements Serializable{

	
	/**
	 * 
	 */
	//private static final long serialVersionUID = -3236019115309574799L;

	@Override
	public String toString() {
		return "ProductDetails [id=" + id + ", product_description=" + product_description + ", currency_code="
				+ currency_code + ", current_price=" + current_price + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String product_description;
	private String currency_code;
	private Double current_price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public Double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(Double current_price) {
		this.current_price = current_price;
	}
	

}

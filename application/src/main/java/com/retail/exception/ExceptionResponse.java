package com.retail.exception;
import java.util.Date;
public class ExceptionResponse {
	public ExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExceptionResponse(Date date,String message, String description) {
		super();
		this.message = message;
		this.description = description;
		this.date = date;
	}
	private Date date;
	private String message;
	private String description;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "ExceptionResponse [message=" + message + ", description=" + description + ", date =" + date
				+ "]";
	}

}

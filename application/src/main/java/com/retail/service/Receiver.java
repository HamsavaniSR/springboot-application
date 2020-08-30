package com.retail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.retail.controller.ProductController;
import com.retail.model.ProductDetails;


@Service
@Component
public class Receiver {
	
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	@Autowired 
	ProductController productController;
	
	// whenever there is msg in the topic , the listener will be called to access this msg
	//@KafkaListener(topics="store1" , containerFactory="kafkaListenerContainerFactory")
	// containerFactory will be given if we use multiple topics
	@KafkaListener(topics="store1")
	public void receive(ProductDetails productDetails) {
		System.out.println("productDetails recieved:"+productDetails.toString());
		logger.info("productDetails recieved:"+productDetails.toString());
		productController.saveProduct(productDetails);  // consume the product and save in db
		System.out.println("productDetails consumed:");

	}

/*	@KafkaListener(topics = "store1")
	public void receive(ProductDetails productDetails) {

		logger.info("received product='{}'", productDetails.toString());
		productController.saveProduct(productDetails);

	}*/
	
}

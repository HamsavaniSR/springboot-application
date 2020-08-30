package com.retail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.retail.model.ProductDetails;

@Service
public class Sender {

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	@Value("${spring.kafka.template.default-topic}")
	private String topic_name;  // topic name
	
	@Autowired 
	public KafkaTemplate<String, ProductDetails> kafkaTemplate;
	
	public void send(ProductDetails productDetails) { // user defined Method
		logger.info("Product Details " +productDetails.toString());
		System.out.println("topic_name"+topic_name);
		this.kafkaTemplate.send(topic_name,productDetails); // send product Details json data to topic
		System.out.println("Send to topic"+topic_name);
	}

}

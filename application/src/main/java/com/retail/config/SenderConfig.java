package com.retail.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.retail.model.ProductDetails;

//Serialize the message before sending to topic .. Need to use Serializer class
// Serialize : convert object to byte stream

@Configuration
public class SenderConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapservers;
	
	@Bean
	public Map<String,Object> producerConfigs()
	{
		Map<String,Object> configProps = new HashMap<String,Object>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers); // server name
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);// datatype of key (Data type of topic name)
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);// datatype of value ( Product Detail is of JSON format)
		return configProps;
	}
	
	
	@Bean
	public ProducerFactory<String, ProductDetails> producerFactory(){
		return new DefaultKafkaProducerFactory<String, ProductDetails>(producerConfigs());
	}
	
	@Bean
	public KafkaTemplate<String, ProductDetails>  kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}

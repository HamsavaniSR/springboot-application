package com.retail.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.retail.model.ProductDetails;
//DeSerialize the message received from topic .. Need to use DeSerializer class
//DESerialize : convert byte stream to object
@Configuration
@EnableKafka // to identify there is listerner
public class ReceiverConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapservers;
	
	// To consume messages, we need consumer configurations
	@Bean
	public Map<String,Object> consumerConfigs()
	{
		Map<String,Object> configProps = new HashMap<String,Object>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers); // server name
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // datatype of key (Data type of topic name)
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // datatype of value ( Product Detail is of JSON format)
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group-store");
		//A unique string that identifies the consumer group this consumer belongs to. 
		//This property is required if the consumer uses either the group management functionality by using subscribe(topic)
		//or the Kafka-based offset management strategy.
		configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//if the current offset does not exist any more on the server (e.g. because that data has been deleted)
		System.out.println("configProps++=="+configProps);
		return configProps;
	}
	
	
	@Bean
	public ConsumerFactory<String, ProductDetails> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),
				new JsonDeserializer<>(ProductDetails.class));// to deserialize json into custom json
	} // need to deserialize the message received from topic into required format
	
	
	@Bean// To listen the msg so that it can inject on required class
	public ConcurrentKafkaListenerContainerFactory<String, ProductDetails> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, ProductDetails> factory =new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}

package com.retail.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.retail.model.ProductDetails;

public interface ProductRepository extends MongoRepository<ProductDetails, Integer> {
	 ProductDetails findProductById (@Param("id") Integer id);
}
